package ru.aliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * DocumentSaver сохраняет дерево XML Document
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 18.05.2023
 */
public class DocumentSaver {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentSaver.class.getSimpleName());

    /**
     * Сохраняет XML дерево DOCUMENT в XML файл.
     *
     * @param file     File
     * @param document Document
     * @throws TransformerException Exception
     */
    public void saveXMLtoFile(File file, Document document) throws TransformerException {
        LOG.info("Save DOCUMENT xml by file: {}", file);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(file);

        transformer.transform(source, result);
    }
}
