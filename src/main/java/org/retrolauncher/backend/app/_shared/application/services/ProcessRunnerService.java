package org.retrolauncher.backend.app._shared.application.services;

import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToLaunchProcessException;

import java.nio.file.Path;

public interface ProcessRunnerService {
    Process startGame(Path gamePath, Path corePath) throws NotAbleToLaunchProcessException;
}
