package ru.aliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;

import java.util.Map;
import java.util.function.Predicate;


/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * DocumentParser редактирование дерева XML файла в виде Документа по заданным условиям.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 18.05.2023
 */
public class DocumentParser {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentParser.class.getSimpleName());

    private static final String ELEMENT_DOM_ROW = "ROW";
    private static final String FUEL = "FUEL";
    private static final Integer FL_1_2 = 2;
    private static final Predicate<Integer> NUM_AZS = (azs) -> azs > 0 && azs <= FL_1_2;

    private static final Map<String, String> FLASH_AZS_1_2 = Map.of(
            "1", "2",
            "2", "1",
            "3", "3"
    );

    private static final Map<String, String> FLASH_AZS_3_38 = Map.of(
            "1", "3",
            "2", "2",
            "3", "1"
    );

    private static final Map<Boolean, Map<String, String>> FUEL_CODE_ALL_AZS = Map.of(
            true, FLASH_AZS_1_2,
            false, FLASH_AZS_3_38
    );


    public Document parseDocumentByAttributesFuel(Document document, Integer azsNumber) {
        LOG.info("Start parse document url: {}, azs_number: {}", document.getDocumentURI(), azsNumber);

        Map<String, String> fuelCode = FUEL_CODE_ALL_AZS.get(NUM_AZS.test(azsNumber));
        Element element = document.getDocumentElement();

        NodeList childrenData = element.getElementsByTagName(ELEMENT_DOM_ROW);

        for (int i = 0; i < childrenData.getLength(); i++) {
            NamedNodeMap attributes = childrenData.item(i).getAttributes();
            Node nodeFuel = attributes.getNamedItem(FUEL);
            String value = nodeFuel.getNodeValue();
            if (fuelCode.containsKey(value)) {
                nodeFuel.setNodeValue(fuelCode.get(value));
            }
        }
        return document;
    }
}
