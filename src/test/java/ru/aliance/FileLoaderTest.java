package ru.aliance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.*;

/**
 * ООО "Альянс розница"
 * Реализация редактора XML.
 * С целью корректной загрузки в процессинге ServioPump
 * отчетов реализации от партнера AЗС Flash
 * FileLoader TEST
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 19.05.2023
 */
class FileLoaderTest {

    private static boolean test(Path path) {
        return false;
    }

    @Test
    void whenAddDirectoryToFileThenEqualsNewFileTrue() {
        FileLoader fileLoader = new FileLoader();
        String newDirectory = "newDirectory";
        File file = new File("oldDirectory/gsmarchiv_3 any_nymber.xml");
        File expectedFile = new File("newDirectory/gsmarchiv_3 any_nymber.xml");
        File actualFile = fileLoader.addDirectoryToFile(newDirectory, file);
        assertThat(actualFile).isEqualTo(expectedFile);
    }

    @Test
    void whenAddDirectoryParentToFileThenEqualsNewFileTrue() {
        FileLoader fileLoader = new FileLoader();
        String newDirectory = "/newDirectory";
        File file = new File("oldDirectory/gsmarchiv_3 any_nymber.xml");
        File expectedFile = new File("/newDirectory/gsmarchiv_3 any_nymber.xml");
        File actualFile = fileLoader.addDirectoryToFile(newDirectory, file);
        assertThat(actualFile).isEqualTo(expectedFile);
    }

    @Test
    void cleanDirectoryThenDirectoryIsEmpty(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source = tempDir.resolve("file1.xml").toFile();
        source.createNewFile();

        assertThat(tempDir.toFile().listFiles().length).isEqualTo(1);

        fileLoader.cleanDirectory(tempDir.toString());
        assertThat(tempDir).isEmptyDirectory();
    }

    @Test
    void cleanDirectoryTwoFileThenDirectoryIsEmpty(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source = tempDir.resolve("file1.xml").toFile();
        source.createNewFile();
        File source1 = tempDir.resolve("file2.xml").toFile();
        source1.createNewFile();

        assertThat(tempDir.toFile().listFiles().length).isEqualTo(2);

        fileLoader.cleanDirectory(tempDir.toString());
        assertThat(tempDir).isEmptyDirectory();
    }

    @Test
    void whenLoadFileByDirectoryAllThenReturnListFiles(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source = tempDir.resolve("file1.xml").toFile();
        source.createNewFile();
        File source1 = tempDir.resolve("file2.xml").toFile();
        source1.createNewFile();

        List<File> expect = List.of(source, source1);
        List<File> actual = fileLoader.loadFileByDirectory(tempDir.toString(), path -> true);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void whenLoadFileByDirectoryOnlyXMLThenReturnListFiles(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source = tempDir.resolve("file1.xml").toFile();
        source.createNewFile();
        File source1 = tempDir.resolve("file2.txt").toFile();
        source1.createNewFile();

        List<File> expect = List.of(source);
        List<File> actual = fileLoader.loadFileByDirectory(tempDir.toString(),
                path -> path.toString().toLowerCase().endsWith(".xml"));

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void whenLoadFileByDirectoryOnlyXMLAndFileNameGSMARCHIVEThenReturnListFiles(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source = tempDir.resolve("GSMARCHIVE_34_ANY_S.xml").toFile();
        source.createNewFile();
        File source1 = tempDir.resolve("file2.txt").toFile();
        source1.createNewFile();
        File source2 = tempDir.resolve("file3.xml").toFile();
        source2.createNewFile();

        List<File> expect = List.of(source);
        List<File> actual = fileLoader.loadFileByDirectory(tempDir.toString(),
                path -> path.toString()
                                .toLowerCase().endsWith(".xml")
                        && path.getFileName()
                                .toString().toLowerCase().startsWith("gsmarchive"));

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void whenLoadFileByDirectoryOnlyXMLAndFileNameGSMARCHIVEAndPERSARCHIVEThenReturnListFiles(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source = tempDir.resolve("GSMARCHIVE_34_ANY_S.xml").toFile();
        source.createNewFile();
        File source1 = tempDir.resolve("file2.txt").toFile();
        source1.createNewFile();
        File source2 = tempDir.resolve("PERSARCHIVE_34_ANY_S.xml").toFile();
        source2.createNewFile();

        List<File> expect = List.of(source, source2);
        List<File> actual = fileLoader.loadFileByDirectory(tempDir.toString(),
                path -> path.toString()
                                .toLowerCase().endsWith(".xml")
                        && (path.getFileName().toString().toLowerCase().startsWith("gsmarchive")
                            || path.getFileName().toString().toLowerCase().startsWith("persarchive")));

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void whenLoadFileByDirectoryPredicateFalseThenReturnEmptyList(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source = tempDir.resolve("GSMARCHIVE_34_ANY_S.xml").toFile();
        source.createNewFile();
        File source1 = tempDir.resolve("file2.txt").toFile();
        source1.createNewFile();
        File source2 = tempDir.resolve("PERSARCHIVE_34_ANY_S.xml").toFile();
        source2.createNewFile();

        List<File> expect = Collections.emptyList();
        List<File> actual = fileLoader.loadFileByDirectory(tempDir.toString(),
                FileLoaderTest::test);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void whenMoveFileThenReturnTrue(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source = tempDir.resolve("file.txt").toFile();
        source.createNewFile();
        File newDir = tempDir.resolve("newDir").toFile();
        newDir.mkdir();
        boolean result = fileLoader.moveFileToDirectory(newDir.getPath().toString(), source);
        assertThat(newDir.listFiles().length).isEqualTo(1);
        assertThat(result).isTrue();
    }

    @Test
    void whenMoveTwoFileThenReturnTrue(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source1 = tempDir.resolve("file1.txt").toFile();
        source1.createNewFile();
        File source2 = tempDir.resolve("file2.txt").toFile();
        source2.createNewFile();
        File newDir = tempDir.resolve("newDir").toFile();
        newDir.mkdir();
        boolean result1 = fileLoader.moveFileToDirectory(newDir.getPath().toString(), source1);
        boolean result2 = fileLoader.moveFileToDirectory(newDir.getPath().toString(), source2);

        assertThat(newDir.listFiles().length).isEqualTo(2);

        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }

    @Test
    void whenMoveFileThenReturnFalse(@TempDir Path tempDir) throws IOException {
        FileLoader fileLoader = new FileLoader();
        File source1 = tempDir.resolve("file1.txt").toFile();
        source1.createNewFile();

        boolean result = fileLoader.moveFileToDirectory("newDir", source1);

        assertThat(result).isFalse();
    }
}