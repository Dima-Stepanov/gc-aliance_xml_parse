package ru.aliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * FileParser класс для поиска символов в имени файла
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 18.05.2023
 */
public class FileNameParser {
    private static final Logger LOG = LoggerFactory.getLogger(FileNameParser.class.getSimpleName());

    private static final String REGEX = "\\d+";

    /**
     * Метод находит первую цифру в имени файла, что в данном случае является номером АЗС
     *
     * @param fileName File name
     * @return int NumberAzs
     */
    public int getNumberAzsByFileName(String fileName) {
        LOG.info("Start parse file name, by find first number: {}", fileName);
        int result = 0;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            result = Integer.parseInt(matcher.group());
        }
        return result;
    }
}
