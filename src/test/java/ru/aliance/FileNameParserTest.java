package ru.aliance;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * FileNameParser TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 18.05.2023
 */
class FileNameParserTest {
    @Test
    void whenGetFirstNumberByStringWhenReturn3() {
        String testString = "GSMARCHIVE_3 11_05_2023 8_01_02 - 11_05_2023 9_01_02.XML";
        FileNameParser fileNameParser = new FileNameParser();
        int actual = fileNameParser.getNumberAzsByFileName(testString);
        int expected = 3;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenGetFirstNumberThenReturnZero() {
        String testString = "GSMARCHIVE_ __ __ - __ __.";
        FileNameParser fileNameParser = new FileNameParser();
        int actual = fileNameParser.getNumberAzsByFileName(testString);
        int expected = 0;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenGetFirstNumberByStringWhenReturn38() {
        String testString = "assdfsdgsgE_38 11_05_2023 8_01_02 - 11_05_2023 9_01_02.XML";
        FileNameParser fileNameParser = new FileNameParser();
        int actual = fileNameParser.getNumberAzsByFileName(testString);
        int expected = 38;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenGetFirstNumberByStringWhenReturn15() {
        String testString = "PERSARCHIV_15 11_05_2023 8_01_02 - 11_05_2023 9_01_02.XML";
        FileNameParser fileNameParser = new FileNameParser();
        int actual = fileNameParser.getNumberAzsByFileName(testString);
        int expected = 15;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void whenGetFirstNumberByStringWhenReturn0() {
        String testString = "PERSARCHIV_0 11_05_2023 8_01_02 - 11_05_2023 9_01_02.XML";
        FileNameParser fileNameParser = new FileNameParser();
        int actual = fileNameParser.getNumberAzsByFileName(testString);
        int expected = 0;
        assertThat(actual).isEqualTo(expected);
    }
}