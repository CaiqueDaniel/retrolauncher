package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.Backend;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.ListGameResponse;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;

import java.util.List;
import java.util.UUID;

public class GamesFacadeImpl implements GamesFacade {
    @Override
    public List<ListGameResponse> listAll() {
        return Backend.getDependencies().getGamesController().listAll();
    }

    @Override
    public void saveCover(SaveGameCoverDto dto) {
        Backend.getDependencies().getGamesController().saveCover(dto);
    }

    @Override
    public void createShortcut(String id) {
        Backend.getDependencies().getGamesController().createShortcut(id);
    }

    @Override
    public void reindexGames() {
        Backend.getDependencies().getGamesController().reindexGames();
    }

    @Override
    public void startGame(UUID id) {
        Backend.getDependencies().getGamesController().startGame(id);
    }
}
