package ru.aliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 18.05.2023
 */
public class MainXml {
    private static final Logger LOG = LoggerFactory.getLogger(MainXml.class.getSimpleName());
    private static final String XML = ".xml";
    private static final String GSM_ARCHIVE = "gsmarchive";
    private static final String PERS_ARCHIVE = "persarchive";
    private String inDir;
    private String outDir;

    public MainXml(String file) {
        initParam(file);
    }

    private void initParam(String file) {
        Properties properties = new Properties();
        try (InputStream in = MainXml.class
                .getClassLoader().getResourceAsStream(file)) {
            properties.load(in);
            this.inDir = properties.getProperty("in");
            this.outDir = properties.getProperty("out");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {


        ArgsSearch argsSearch = ArgsSearch.of(args);
        MainXml mainXml = new MainXml(argsSearch.get("p"));


        FileLoader fileLoader = new FileLoader();
        FileNameParser fileNameParser = new FileNameParser();
        DocumentLouder documentLouder = new DocumentLouder();
        DocumentParser documentParser = new DocumentParser();
        DocumentSaver documentSaver = new DocumentSaver();


        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        List<File> xmlGsm = fileLoader.loadFileByDirectory(mainXml.inDir,
                path -> path.getFileName().toString().toLowerCase().endsWith(XML)
                        && path.getFileName().toString().toLowerCase().startsWith(GSM_ARCHIVE));
        List<File> xmlPers = fileLoader.loadFileByDirectory(mainXml.inDir,
                path -> path.getFileName().toString().toLowerCase().endsWith(XML)
                        && path.getFileName().toString().toLowerCase().startsWith(PERS_ARCHIVE));


        xmlPers.forEach(file -> executor.submit(
                () -> fileLoader.moveFileToDirectory(mainXml.outDir, file)));


        for (File file : xmlGsm) {
            executor.submit(() -> {
                int azsNumber = fileNameParser.getNumberAzsByFileName(file.getPath());
                try {
                    Document document = documentLouder.getDocumentByFile(file);
                    documentParser.parseDocumentByAttributesFuel(document, azsNumber);
                    File newFile = fileLoader.addDirectoryToFile(mainXml.outDir, file);
                    documentSaver.saveXMLtoFile(newFile, document);
                } catch (Exception e) {
                    LOG.error("Error application: {}", e);
                    throw new RuntimeException(e);
                }
            });
        }


        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                LOG.error("Executor shutdown error: {}", e);
                throw new RuntimeException(e);
            }
        }
        executor.shutdownNow();


        fileLoader.cleanDirectory(mainXml.inDir);
    }
}
