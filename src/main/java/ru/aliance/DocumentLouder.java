package ru.aliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * DocumentLouder класс создает Document
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 18.05.2023
 */
public class DocumentLouder {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentLouder.class.getSimpleName());

    /**
     * Создание объекта Document на основании файла XML
     *
     * @param xmlFile File dir+fileXml
     * @return Document
     * @throws ParserConfigurationException Exception
     * @throws IOException                  Exception
     * @throws SAXException                 Exception
     */
    public Document getDocumentByFile(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        LOG.info("Load to DOCUMENT by XML file: {}", xmlFile);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(xmlFile);
    }
}
