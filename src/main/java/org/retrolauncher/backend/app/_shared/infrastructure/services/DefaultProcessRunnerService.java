package org.retrolauncher.backend.app._shared.infrastructure.services;

import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToLaunchProcessException;
import org.retrolauncher.backend.app._shared.application.services.ProcessRunnerService;

import java.nio.file.Path;

public class DefaultProcessRunnerService implements ProcessRunnerService {
    @Override
    public Process startGame(Path gamePath, Path corePath) throws NotAbleToLaunchProcessException {
        try {
            Path retroarchPath = corePath
                    .getParent()
                    .getParent()
                    .resolve("retroarch.exe");

            return new ProcessBuilder()
                    .command(
                            retroarchPath.toString(),
                            "-L",
                            corePath.toAbsolutePath().toString(),
                            gamePath.toAbsolutePath().toString()
                    ).start();
        } catch (Exception exception) {
            throw new NotAbleToLaunchProcessException(exception);
        }
    }
}
