package integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.retrolauncher.backend.app.settings.application.SaveSettingsUseCase;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.dtos.SaveSettingsInputDto;

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

class StubSettingRepository implements SettingRepository {
    private Setting setting;

    @Override
    public void save(Setting entity) {
        this.setting = entity;
    }

    public Optional<Setting> get() {
        return Optional.of(this.setting);
    }
}
