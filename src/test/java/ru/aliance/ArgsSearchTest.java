package ru.aliance;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 19.05.2023
 */
class ArgsSearchTest {
    @Test
    public void whenGetParamThenTrue() {
        String[] args = new String[]{
                "-p=file.properties",
        };
        ArgsSearch argsSearch = ArgsSearch.of(args);
        assertThat(argsSearch.get("p")).isEqualTo("file.properties");
    }

    @Test
    public void whenNotEnoughParametersThenExcaption() {
        String[] args = new String[]{
                "-p=file.properties",
                "-p=file.properties"
        };
        assertThatThrownBy(() -> ArgsSearch.of(args))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenParameterIsSetIncorrectlyThenException() {
        String[] args = new String[]{
                "-d=",
        };
        assertThatThrownBy(() -> ArgsSearch.of(args))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenParameterWasNotFoundThenException() {
        String[] args = new String[]{
                "-d=directory"};
        ArgsSearch argsSearch = ArgsSearch.of(args);
        assertThatThrownBy(() -> argsSearch.get("p"))
                .isInstanceOf(NoSuchElementException.class);
    }
}