package ru.aliance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * DocumentLouder Test
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 19.05.2023
 */
class DocumentLouderTest {

    @Test
    void whenGetDOMDocumentXmlFileThenReturnDocument(@TempDir Path tempDir) throws Exception {
        String dataXmlFile = "<ROWDATA><ROW FUEL=\"3\"/></ROWDATA>";
        File fileXml = tempDir.resolve("xmlFile.xml").toFile();
        try (PrintWriter out = new PrintWriter(fileXml)) {
            out.print(dataXmlFile);
        }
        DocumentLouder documentLouder = new DocumentLouder();
        Document actual = documentLouder.getDocumentByFile(fileXml);

        Document expected = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileXml);

        assertThat(actual.getDocumentURI()).isEqualTo(expected.getDocumentURI());
    }

}