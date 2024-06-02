package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.Backend;
import org.retrolauncher.backend.app.games.infrastructure.desktop.controllers.GamesController;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.ListGameResponse;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.UpdateGameRequestDto;

import java.util.List;
import java.util.UUID;

public class GamesFacadeImpl implements GamesFacade {
    private final GamesController controller = Backend.getDependencies().getGamesController();

    @Override
    public List<ListGameResponse> listAll() {
        return controller.listAll();
    }

    @Override
    public void saveCover(SaveGameCoverDto dto) {
        controller.saveCover(dto);
    }

    @Override
    public void createShortcut(String id) {
        controller.createShortcut(id);
    }

    @Override
    public void reindexGames() {
        controller.reindexGames();
    }

    @Override
    public void startGame(UUID id) {
        controller.startGame(id);
    }

    @Override
    public void updateGame(UpdateGameRequestDto dto) throws RuntimeException {
        controller.updateGame(dto);
    }
}
