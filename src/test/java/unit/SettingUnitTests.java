package unit;

import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.exceptions.SettingValidationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SettingUnitTests {
    private final File testFolder = new File("test");

    @BeforeAll
    void beforeAll() {
        testFolder.mkdirs();
    }

    @AfterEach()
    void afterEach() {
        Arrays.stream(Objects.requireNonNull(testFolder.listFiles())).toList().forEach(File::delete);
    }

    @AfterAll
    void afterAll() {
        testFolder.delete();
    }

    @Test
    void it_should_be_able_to_create() {
        Setting setting = new Setting(testFolder.toPath(), testFolder.toPath());
        assertEquals(testFolder.getAbsolutePath(), setting.getRetroarchFolderPath().toAbsolutePath().toString());
        assertEquals(testFolder.getAbsolutePath(), setting.getRomsFolderPath().toAbsolutePath().toString());
    }

    @Test
    void it_should_not_be_able_to_create_given_rom_path_is_file_instead_of_a_folder() throws IOException {
        Path invalidFile = Path.of(testFolder.getAbsoluteFile().getPath()).resolve("file.test");
        invalidFile.toFile().createNewFile();
        assertThrows(SettingValidationException.class, () -> new Setting(invalidFile, testFolder.toPath()));
    }

    @Test
    void it_should_not_be_able_to_create_given_rom_path_is_invalid() throws IOException {
        Path invalidFile = Path.of(testFolder.getAbsoluteFile().getPath()).resolve("folder");
        assertThrows(SettingValidationException.class, () -> new Setting(invalidFile, testFolder.toPath()));
    }

    @Test
    void it_should_not_be_able_to_create_given_retroarch_path_is_file_instead_of_a_folder() throws IOException {
        Path invalidFile = Path.of(testFolder.getAbsoluteFile().getPath()).resolve("file.test");
        invalidFile.toFile().createNewFile();
        assertThrows(SettingValidationException.class, () -> new Setting(testFolder.toPath(), invalidFile));
    }

    @Test
    void it_should_not_be_able_to_create_given_retroarch_path_is_invalid() throws IOException {
        Path invalidFile = Path.of(testFolder.getAbsoluteFile().getPath()).resolve("folder");
        assertThrows(SettingValidationException.class, () -> new Setting(testFolder.toPath(), invalidFile));
    }
}
