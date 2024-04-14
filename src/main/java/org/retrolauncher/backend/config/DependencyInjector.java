package org.retrolauncher.backend.config;

import lombok.Getter;
import org.retrolauncher.backend.app._shared.application.services.EnvConfigService;
import org.retrolauncher.backend.app._shared.application.services.EventDispatcherService;
import org.retrolauncher.backend.app._shared.application.services.ProcessRunnerService;
import org.retrolauncher.backend.app._shared.application.services.ShortcutService;
import org.retrolauncher.backend.app._shared.infrastructure.services.*;
import org.retrolauncher.backend.app.games.application.usecases.*;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.repositories.JacksonGameRepository;
import org.retrolauncher.backend.app.games.infrastructure.desktop.controllers.GamesController;
import org.retrolauncher.backend.app.platforms.application.services.PlatformsResourceConfigService;
import org.retrolauncher.backend.app.platforms.application.usecases.UpdatePlatformsListUseCase;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.models.PlatformModel;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.repositories.JacksonPlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.services.FilePlatformResourceConfigService;
import org.retrolauncher.backend.app.settings.application.usecases.GetSettingsUseCase;
import org.retrolauncher.backend.app.settings.application.usecases.SaveSettingsUseCase;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model.SettingModel;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.repositories.JacksonSettingRepository;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.controllers.SettingController;
import org.retrolauncher.backend.database.JacksonFileDatabaseDriver;
import org.retrolauncher.backend.events.DefaultEventManager;

@Getter
public class DependencyInjector {
    private final PlatformRepository platformRepository;
    private final GameRepository gameRepository;
    private final SettingRepository settingRepository;

    private final PlatformsResourceConfigService platformsResourceConfigService;
    private final EnvConfigService configService;
    private final ShortcutService shortcutService;
    private final ProcessRunnerService processRunnerService;
    private final EventDispatcherService eventDispatcherService;

    private final UpdatePlatformsListUseCase updatePlatformsListUseCase;
    private final UpdateGamesListUseCase updateGamesListUseCase;
    private final StartGameUseCase startGameUseCase;
    private final ListGamesUseCase listGamesUseCase;
    private final CreateGameShortcutUseCase createGameShortcutUseCase;
    private final SaveGameCoverUseCase saveGameCoverUseCase;
    private final SaveSettingsUseCase saveSettingsUseCase;
    private final GetSettingsUseCase getSettingsUseCase;

    private final GamesController gamesController;
    private final SettingController settingController;

    public DependencyInjector() {
        this.configService = new ProdEnvConfigService();
        this.shortcutService = new ShellLinkShortcutService();
        this.processRunnerService = new DefaultProcessRunnerService(this.configService);
        this.eventDispatcherService = new DefaultEventDispatcherService(DefaultEventManager.getInstance());
        this.platformRepository = new JacksonPlatformRepository(new JacksonFileDatabaseDriver<>(PlatformModel.class));
        this.gameRepository = new JacksonGameRepository(
                new JacksonFileDatabaseDriver<>(GameModel.class),
                this.platformRepository
        );
        this.settingRepository = new JacksonSettingRepository(new JacksonFileDatabaseDriver<>(SettingModel.class));
        this.platformsResourceConfigService = new FilePlatformResourceConfigService();

        this.updatePlatformsListUseCase = new UpdatePlatformsListUseCase(
                this.platformRepository,
                this.settingRepository,
                this.platformsResourceConfigService
        );
        this.updateGamesListUseCase = new UpdateGamesListUseCase(
                this.gameRepository,
                this.platformRepository,
                this.settingRepository
        );
        this.startGameUseCase = new StartGameUseCase(this.gameRepository, this.processRunnerService);
        this.listGamesUseCase = new ListGamesUseCase(this.gameRepository);
        this.createGameShortcutUseCase = new CreateGameShortcutUseCase(this.gameRepository, this.shortcutService);
        this.saveGameCoverUseCase = new SaveGameCoverUseCase(this.gameRepository, new CoverUploaderService());
        this.saveSettingsUseCase = new SaveSettingsUseCase(this.settingRepository, this.eventDispatcherService);
        this.getSettingsUseCase = new GetSettingsUseCase(this.settingRepository);
        this.gamesController = new GamesController(
                this.listGamesUseCase,
                this.saveGameCoverUseCase,
                this.createGameShortcutUseCase,
                this.updateGamesListUseCase
        );
        this.settingController = new SettingController(this.saveSettingsUseCase, this.getSettingsUseCase);
    }
}
