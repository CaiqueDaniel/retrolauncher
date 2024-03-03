package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.app.games.application.dtos.GameInfoOutputDto;

import java.util.List;

public interface GamesFacade {
    List<GameInfoOutputDto> listAll();
}
