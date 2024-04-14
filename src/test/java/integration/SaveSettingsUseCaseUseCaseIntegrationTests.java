package integration;

import fixtures.StubSettingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.retrolauncher.backend.app._shared.application.services.EventDispatcherService;
import org.retrolauncher.backend.app.settings.application.dtos.SaveSettingsInputDto;
import org.retrolauncher.backend.app.settings.application.usecases.SaveSettingsUseCase;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveSettingsUseCaseUseCaseIntegrationTests {
    private final StubSettingRepository repository = new StubSettingRepository();
    private final EventDispatcherService eventDispatcherService = mock(EventDispatcherService.class);
    private final SaveSettingsUseCase saveSettingsUseCase = new SaveSettingsUseCase(repository, eventDispatcherService);

    @Test
    void it_should_be_able_to_save_settings() {
        SaveSettingsInputDto dto = new SaveSettingsInputDto("C:/roms", "C:/retroarch");
        saveSettingsUseCase.execute(dto);
        Optional<Setting> result = repository.get();
        assertEquals("C:\\roms", result.get().getRomsFolderPath().toString());
        assertEquals("C:\\retroarch", result.get().getRetroarchFolderPath().toString());
    }
}
