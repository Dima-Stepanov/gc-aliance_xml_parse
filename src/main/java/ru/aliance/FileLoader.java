package ru.aliance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * FileLoader класс загрузки и удаления файлов
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 18.05.2023
 */
public class FileLoader {
    private static final Logger LOG = LoggerFactory.getLogger(FileLoader.class.getSimpleName());

    /**
     * Создание списка файлов в указанном каталоге которые удовлетворяют условию
     *
     * @param inDirectory String catalog search
     * @param pathFilter  Predicate
     * @return List File
     */
    public List<File> loadFileByDirectory(String inDirectory, Predicate<Path> pathFilter) {
        LOG.info("Load file by directory: {}, if Predicate exists: {}", inDirectory, pathFilter.toString());
        List<File> result;
        try (Stream<Path> paths = Files.walk(Paths.get(inDirectory))) {
            result = paths.filter(Files::isRegularFile)
                    .filter(pathFilter::test)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOG.error("Load file by directory: {}, ERROR: {}", inDirectory, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Создание списка файлов привязанных к новому каталогу
     *
     * @param directory String catalog new
     * @param file      File
     * @return List File
     */
    public File addDirectoryToFile(String directory, File file) {
        LOG.info("Change catalog by file. New CATALOG: {}, FILES: {}", directory, file.getName());
        return new File(directory + "/" + file.getName());
    }

    public boolean moveFileToDirectory(String directory, File file) {
        File moveFile = addDirectoryToFile(directory, file);
        return file.renameTo(moveFile);
    }

    /**
     * Удаляет все файлы в заданном каталоге
     *
     * @param directory String clear catalog
     */
    public void cleanDirectory(String directory) {
        LOG.info("Delete all files by catalog: {}", directory);
        loadFileByDirectory(directory, Path -> true)
                .stream()
                .forEach(File::delete);
    }
}
