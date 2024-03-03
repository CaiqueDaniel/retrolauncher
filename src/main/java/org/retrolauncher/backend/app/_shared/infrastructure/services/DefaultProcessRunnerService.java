package org.retrolauncher.backend.app._shared.infrastructure.services;

import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToLaunchProcessException;
import org.retrolauncher.backend.app._shared.application.services.EnvConfigService;
import org.retrolauncher.backend.app._shared.application.services.ProcessRunnerService;
import org.retrolauncher.backend.app.games.domain.entities.Game;

public class DefaultProcessRunnerService implements ProcessRunnerService {
    private final EnvConfigService configService;

    public DefaultProcessRunnerService(EnvConfigService configService) {
        this.configService = configService;
    }

    @Override
    public Process startGame(Game game) throws NotAbleToLaunchProcessException {
        try {
            return new ProcessBuilder()
                    .command(
                            this.configService.getRetroArchPath(),
                            "-L",
                            game.getPlatform().getCorePath(),
                            game.getPath()
                    ).start();
        } catch (Exception exception) {
            throw new NotAbleToLaunchProcessException(exception);
        }
    }
}
