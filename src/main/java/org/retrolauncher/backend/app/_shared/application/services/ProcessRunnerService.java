package org.retrolauncher.backend.app._shared.application.services;

import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToLaunchProcessException;
import org.retrolauncher.backend.app.games.domain.entities.Game;

public interface ProcessRunnerService {
    Process startGame(Game game) throws NotAbleToLaunchProcessException;
}
