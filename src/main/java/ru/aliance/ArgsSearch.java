package ru.aliance;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * ArgsSearch валидация параметры командной строки запуска приложения
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 19.05.2023
 */
public class ArgsSearch {
    final private Map<String, String> values = new HashMap<>();

    /**
     * Делаем недоступным конструктор по умолчанию.
     */
    private ArgsSearch() {
    }

    /**
     * Собираем параметры в Map.
     *
     * @param args String[].
     */
    private void parse(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Enter the missing parameters."
                                               + "Format: -p=file.properties");
        }
        for (String arg : args) {
            String[] param = arg.split("=");
            if (param.length != 2) {
                throw new IllegalArgumentException("Parameter is not correct. Usage -key=value");
            }
            param[0] = param[0].replaceFirst("-", "");
            this.values.put(param[0], param[1]);
        }
    }

    /**
     * Возвращаем параметры по ключу.
     *
     * @param key String.
     * @return values.
     */
    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new NoSuchElementException("The " + key + " parameter was not found.");
        }
        return values.get(key);
    }

    /**
     * Создание и сборка параметров.
     *
     * @param args String[].
     * @return ArgsSearch
     */
    public static ArgsSearch of(String[] args) {
        ArgsSearch argsSearch = new ArgsSearch();
        argsSearch.parse(args);
        return argsSearch;
    }
}
