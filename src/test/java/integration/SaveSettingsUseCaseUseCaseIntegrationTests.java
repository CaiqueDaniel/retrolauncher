package integration;

import fixtures.StubSettingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;
import org.retrolauncher.backend.app.settings.application.usecases.SaveSettingsUseCase;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveSettingsUseCaseUseCaseIntegrationTests {
    private final StubSettingRepository repository = new StubSettingRepository();
    private final SaveSettingsUseCase saveSettingsUseCase = new SaveSettingsUseCase(repository);

    @Test
    void it_should_be_able_to_save_settings() {
        SaveSettingsInputDto dto = new SaveSettingsInputDto("C:/roms", "C:/retroarch");
        saveSettingsUseCase.execute(dto);
        Optional<Setting> result = repository.get();
        assertTrue(result.get().getRomsFolderPath().toString().equals("C:\\roms"));
        assertTrue(result.get().getRetroarchFolderPath().toString().equals("C:\\retroarch"));
    }
}
