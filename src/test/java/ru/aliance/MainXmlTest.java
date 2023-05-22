package ru.aliance;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 22.05.2023
 */
class MainXmlTest {

    @Test
    void whenSetNoFileParamThenException() {
        assertThatThrownBy(() -> new MainXml(""))
                .isInstanceOf(RuntimeException.class);
    }
}