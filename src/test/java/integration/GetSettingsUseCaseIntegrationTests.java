package integration;

import fixtures.StubSettingRepository;
import org.junit.jupiter.api.*;
import org.retrolauncher.backend.app.settings.application.dtos.GetSettingsOutputDto;
import org.retrolauncher.backend.app.settings.application.exceptions.SettingNotFoundException;
import org.retrolauncher.backend.app.settings.application.usecases.GetSettingsUseCase;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetSettingsUseCaseIntegrationTests {
    private final StubSettingRepository repository = new StubSettingRepository();
    private final GetSettingsUseCase sut = new GetSettingsUseCase(repository);

    @AfterEach
    void afterEach() {
        repository.clear();
    }

    @Test
    void it_should_be_able_to_get_settings() {
        repository.save(new Setting("C:/roms", "C:/retroarch"));
        GetSettingsOutputDto result = sut.execute();
        assertEquals("C:\\roms", result.romsFolderPath());
        assertEquals("C:\\retroarch", result.retroarchFolderPath());
    }

    @Test
    void it_should_not_be_able_to_get_settings_given_there_is_none() {
        assertThrows(SettingNotFoundException.class, sut::execute);
    }
}