package org.retrolauncher.backend.config;

import lombok.Getter;
import org.retrolauncher.backend.app._shared.application.services.*;
import org.retrolauncher.backend.app._shared.infrastructure.services.*;
import org.retrolauncher.backend.app.games.application.services.CoverFileManagerService;
import org.retrolauncher.backend.app.games.application.usecases.*;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.repositories.HibernateGameQueryRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.repositories.HibernateGameRepository;
import org.retrolauncher.backend.app.games.infrastructure.factories.DefaultGameFinderFactory;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.repositories.HibernatePlatformRepository;
import org.retrolauncher.backend.app.settings.application.usecases.*;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.games.infrastructure.desktop.controllers.GamesController;
import org.retrolauncher.backend.app.platforms.application.services.PlatformsResourceConfigService;
import org.retrolauncher.backend.app.platforms.application.usecases.UpdatePlatformsListUseCase;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.platforms.infrastructure.desktop.controllers.PlatformController;
import org.retrolauncher.backend.app.platforms.infrastructure.services.FilePlatformResourceConfigService;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model.SettingModel;
import org.retrolauncher.backend.app.settings.infrastructure.database.jackson.repositories.JacksonSettingRepository;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.controllers.SettingController;
import org.retrolauncher.backend.database.HibernateDriver;
import org.retrolauncher.backend.database.JacksonFileDatabaseDriver;
import org.retrolauncher.backend.events.DefaultEventManager;

import java.io.IOException;

@Getter
public class DependencyInjector {
    private final HibernateDriver hibernateDriver = new HibernateDriver();
    private final PlatformRepository platformRepository;
    private final GameRepository gameRepository;
    private final SettingRepository settingRepository;

    private final PlatformsResourceConfigService platformsResourceConfigService;
    private final ShortcutService shortcutService;
    private final ProcessRunnerService processRunnerService;
    private final EventDispatcherService eventDispatcherService;
    private final FileManagerService coverFileManagerService;

    private final UpdatePlatformsListUseCase updatePlatformsListUseCase;
    private final UpdateGamesListUseCase updateGamesListUseCase;
    private final StartGameUseCase startGameUseCase;
    private final ListGamesUseCase listGamesUseCase;
    private final CreateGameShortcutUseCase createGameShortcutUseCase;
    private final SaveGameCoverUseCase saveGameCoverUseCase;
    private final SaveSettingsUseCase saveSettingsUseCase;
    private final GetSettingsUseCase getSettingsUseCase;
    private final UpdateGameUseCase updateGameUseCase;

    private final PlatformController platformController;
    private final GamesController gamesController;
    private final SettingController settingController;

    public DependencyInjector() throws IOException {
        this.shortcutService = new ShellLinkShortcutService();
        this.processRunnerService = new DefaultProcessRunnerService();
        this.eventDispatcherService = new DefaultEventDispatcherService(DefaultEventManager.getInstance());
        this.coverFileManagerService = new CoverFileManagerService();
        this.platformRepository = new HibernatePlatformRepository(hibernateDriver.getSessionFactory());
        this.gameRepository = new HibernateGameRepository(hibernateDriver.getSessionFactory());
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
                this.settingRepository,
                this.coverFileManagerService,
                new DefaultGameFinderFactory()
        );
        this.startGameUseCase = new StartGameUseCase(gameRepository, platformRepository, processRunnerService);
        this.listGamesUseCase = new ListGamesUseCase(new HibernateGameQueryRepository(hibernateDriver.getSessionFactory()));
        this.createGameShortcutUseCase = new CreateGameShortcutUseCase(this.gameRepository, this.shortcutService);
        this.saveGameCoverUseCase = new SaveGameCoverUseCase(this.gameRepository, new CoverFileSystemService());
        this.saveSettingsUseCase = new SaveSettingsUseCase(this.settingRepository, this.eventDispatcherService);
        this.getSettingsUseCase = new GetSettingsUseCase(this.settingRepository);
        this.updateGameUseCase = new UpdateGameUseCase(this.gameRepository);
        this.platformController = new PlatformController(this.updatePlatformsListUseCase);
        this.gamesController = new GamesController(
                this.listGamesUseCase,
                this.saveGameCoverUseCase,
                this.createGameShortcutUseCase,
                this.updateGamesListUseCase,
                this.startGameUseCase,
                this.updateGameUseCase
        );
        this.settingController = new SettingController(this.saveSettingsUseCase, this.getSettingsUseCase);
    }
}
