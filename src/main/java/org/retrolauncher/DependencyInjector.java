package org.retrolauncher;

import lombok.Getter;
import org.retrolauncher.app._shared.application.services.EnvConfigService;
import org.retrolauncher.app._shared.application.services.ProcessRunnerService;
import org.retrolauncher.app._shared.application.services.ShortcutService;
import org.retrolauncher.app._shared.infrastructure.services.DefaultProcessRunnerService;
import org.retrolauncher.app._shared.infrastructure.services.ProdEnvConfigService;
import org.retrolauncher.app._shared.infrastructure.services.ShellLinkShortcutService;
import org.retrolauncher.app.games.application.usecases.CreateGameShortcutUseCase;
import org.retrolauncher.app.games.application.usecases.ListGamesUseCase;
import org.retrolauncher.app.games.application.usecases.StartGameUseCase;
import org.retrolauncher.app.games.domain.repositories.GameRepository;
import org.retrolauncher.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.app.games.infrastructure.database.jackson.repositories.JacksonGameRepository;
import org.retrolauncher.app.games.application.usecases.UpdateGamesListUseCase;
import org.retrolauncher.app.platforms.application.services.PlatformsResourceConfigService;
import org.retrolauncher.app.platforms.application.usecases.UpdatePlatformsListUseCase;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.models.PlatformModel;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.repositories.JacksonPlatformRepository;
import org.retrolauncher.app.platforms.infrastructure.services.FilePlatformResourceConfigService;
import org.retrolauncher.database.JacksonFileDatabaseDriver;

@Getter
public class DependencyInjector {
    private final PlatformRepository platformRepository;
    private final GameRepository gameRepository;
    private final PlatformsResourceConfigService platformsResourceConfigService;
    private final EnvConfigService configService;
    private final ShortcutService shortcutService;
    private final ProcessRunnerService processRunnerService;
    private final UpdatePlatformsListUseCase updatePlatformsListUseCase;
    private final UpdateGamesListUseCase updateGamesListUseCase;
    private final StartGameUseCase startGameUseCase;
    private final ListGamesUseCase listGamesUseCase;
    private final CreateGameShortcutUseCase createGameShortcutUseCase;

    public DependencyInjector() {
        this.configService = new ProdEnvConfigService();
        this.shortcutService = new ShellLinkShortcutService();
        this.processRunnerService = new DefaultProcessRunnerService(this.configService);
        this.platformRepository = new JacksonPlatformRepository(new JacksonFileDatabaseDriver<>(PlatformModel.class));
        this.gameRepository = new JacksonGameRepository(
                new JacksonFileDatabaseDriver<>(GameModel.class),
                this.platformRepository
        );
        this.platformsResourceConfigService = new FilePlatformResourceConfigService();

        this.updatePlatformsListUseCase = new UpdatePlatformsListUseCase(
                this.platformRepository,
                this.platformsResourceConfigService
        );
        this.updateGamesListUseCase = new UpdateGamesListUseCase(this.gameRepository, this.platformRepository);
        this.startGameUseCase = new StartGameUseCase(this.gameRepository, this.processRunnerService);
        this.listGamesUseCase = new ListGamesUseCase(this.gameRepository);
        this.createGameShortcutUseCase = new CreateGameShortcutUseCase(this.gameRepository, this.shortcutService);
    }
}
