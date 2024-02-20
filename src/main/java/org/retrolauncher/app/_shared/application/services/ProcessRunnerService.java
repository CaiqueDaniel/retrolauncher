package org.retrolauncher.app._shared.application.services;

import org.retrolauncher.app._shared.application.exceptions.NotAbleToLaunchProcessException;
import org.retrolauncher.app.games.domain.entities.Game;

public interface ProcessRunnerService {
    Process startGame(Game game) throws NotAbleToLaunchProcessException;
}
