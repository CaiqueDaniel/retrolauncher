package org.retrolauncher.backend.app._shared.infrastructure.services;

import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToLaunchProcessException;
import org.retrolauncher.backend.app._shared.application.services.EnvConfigService;
import org.retrolauncher.backend.app._shared.application.services.ProcessRunnerService;
import org.retrolauncher.backend.app.games.domain.entities.Game;

import java.nio.file.Path;

public class DefaultProcessRunnerService implements ProcessRunnerService {
    @Override
    public Process startGame(Game game) throws NotAbleToLaunchProcessException {
        try {
            Path retroarchPath = Path.of(game.getPlatform().getCorePath())
                    .getParent()
                    .getParent()
                    .resolve("retroarch.exe");

            return new ProcessBuilder()
                    .command(
                            retroarchPath.toString(),
                            "-L",
                            game.getPlatform().getCorePath(),
                            game.getPath()
                    ).start();
        } catch (Exception exception) {
            throw new NotAbleToLaunchProcessException(exception);
        }
    }
}
