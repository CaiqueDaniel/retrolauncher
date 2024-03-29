package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.app.games.application.dtos.GameInfoOutputDto;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;

import java.util.List;

public interface GamesFacade {
    List<GameInfoOutputDto> listAll();

    void saveCover(SaveGameCoverDto dto);

    void createShortcut(String id);
}
