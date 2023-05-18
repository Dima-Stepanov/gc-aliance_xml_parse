package ru.aliance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * DocumentParser Test
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 19.05.2023
 */
class DocumentParserTest {

    @Test
    void whenParseDocumentByAttributesFuel1OrAzsNumber2ThenAttributeSet2(@TempDir Path tempDir) throws Exception {
        String dataXmlFile = "<ROWDATA><ROW FUEL=\"1\"/></ROWDATA>";
        File fileXml = tempDir.resolve("xmlFile.xml").toFile();
        try (PrintWriter out = new PrintWriter(fileXml)) {
            out.print(dataXmlFile);
        }
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileXml);

        DocumentParser documentParser = new DocumentParser();
        Document actual = documentParser.parseDocumentByAttributesFuel(document, 2);
        String expectFuel = "2";
        String actualFuel = actual.getDocumentElement()
                .getElementsByTagName("ROW").item(0).
                getAttributes().getNamedItem("FUEL").getNodeValue();

        assertThat(actualFuel).isEqualTo(expectFuel);
    }

    @Test
    void whenParseDocumentByAttributesFuel2OrAzsNumber1ThenAttributeSet1(@TempDir Path tempDir) throws Exception {
        String dataXmlFile = "<ROWDATA><ROW FUEL=\"2\"/></ROWDATA>";
        File fileXml = tempDir.resolve("xmlFile.xml").toFile();
        try (PrintWriter out = new PrintWriter(fileXml)) {
            out.print(dataXmlFile);
        }
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileXml);

        DocumentParser documentParser = new DocumentParser();
        Document actual = documentParser.parseDocumentByAttributesFuel(document, 2);
        String expectFuel = "1";
        String actualFuel = actual.getDocumentElement()
                .getElementsByTagName("ROW").item(0).
                getAttributes().getNamedItem("FUEL").getNodeValue();

        assertThat(actualFuel).isEqualTo(expectFuel);
    }

    @Test
    void whenParseDocumentByAttributesFuel1OrAzsNumber3ThenAttributeSet3(@TempDir Path tempDir) throws Exception {
        String dataXmlFile = "<ROWDATA><ROW FUEL=\"1\"/></ROWDATA>";
        File fileXml = tempDir.resolve("xmlFile.xml").toFile();
        try (PrintWriter out = new PrintWriter(fileXml)) {
            out.print(dataXmlFile);
        }
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileXml);

        DocumentParser documentParser = new DocumentParser();
        Document actual = documentParser.parseDocumentByAttributesFuel(document, 3);
        String expectFuel = "3";
        String actualFuel = actual.getDocumentElement()
                .getElementsByTagName("ROW").item(0).
                getAttributes().getNamedItem("FUEL").getNodeValue();

        assertThat(actualFuel).isEqualTo(expectFuel);
    }

    @Test
    void whenParseDocumentByAttributesFuel3orAzsNumber38ThenAttributeSet1(@TempDir Path tempDir) throws Exception {
        String dataXmlFile = "<ROWDATA><ROW FUEL=\"3\"/></ROWDATA>";
        File fileXml = tempDir.resolve("xmlFile.xml").toFile();
        try (PrintWriter out = new PrintWriter(fileXml)) {
            out.print(dataXmlFile);
        }
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileXml);

        DocumentParser documentParser = new DocumentParser();
        Document actual = documentParser.parseDocumentByAttributesFuel(document, 38);
        String expectFuel = "1";
        String actualFuel = actual.getDocumentElement()
                .getElementsByTagName("ROW").item(0).
                getAttributes().getNamedItem("FUEL").getNodeValue();

        assertThat(actualFuel).isEqualTo(expectFuel);
    }
}