package ru.aliance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * DocumentSaver TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 19.05.2023
 */
class DocumentSaverTest {

    @Test
    void whenSaveXMLtoFileThenReturnActualData(@TempDir Path tempDir) throws Exception {
        String dataXmlFile = "<ROWDATA><ROW FUEL=\"3\"/></ROWDATA>";
        File fileXml = tempDir.resolve("xmlFile.xml").toFile();
        File newFileXml = tempDir.resolve("newFileXml").toFile();
        try (PrintWriter out = new PrintWriter(fileXml)) {
            out.print(dataXmlFile);
        }
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileXml);

        DocumentSaver documentSaver = new DocumentSaver();
        documentSaver.saveXMLtoFile(newFileXml, document);
        String actualXml = Files.readString(Path.of(newFileXml.toString()));

        assertThat(actualXml).isEqualTo(dataXmlFile);
    }
}