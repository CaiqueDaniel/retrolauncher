package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.Backend;
import org.retrolauncher.backend.app.games.application.dtos.GameInfoOutputDto;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;

import java.util.List;

public class GamesFacadeImpl implements GamesFacade {
    @Override
    public List<GameInfoOutputDto> listAll() {
        return Backend.getDependencies().getGamesController().listAll();
    }

    @Override
    public void saveCover(SaveGameCoverDto dto) {
        Backend.getDependencies().getGamesController().saveCover(dto);
    }
}
