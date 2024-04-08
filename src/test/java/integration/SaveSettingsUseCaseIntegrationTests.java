package integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.retrolauncher.backend.app.settings.application.SaveSettings;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.dtos.SaveSettingsInputDto;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveSettingsUseCaseIntegrationTests {
    private final StubSettingRepository repository = new StubSettingRepository();
    private final SaveSettings saveSettings = new SaveSettings(repository);

    @Test
    void it_should_be_able_to_save_settings() {
        SaveSettingsInputDto dto = new SaveSettingsInputDto("C:/roms", "C:/retroarch");
        saveSettings.execute(dto);
        Setting result = repository.get();
        assertTrue(result.getRomsFolderPath().toString().equals("C:\\roms"));
        assertTrue(result.getRetroarchFolderPath().toString().equals("C:\\retroarch"));
    }
}

class StubSettingRepository implements SettingRepository {
    private Setting setting;

    @Override
    public void save(Setting entity) {
        this.setting = entity;
    }

    public Setting get() {
        return this.setting;
    }
}
