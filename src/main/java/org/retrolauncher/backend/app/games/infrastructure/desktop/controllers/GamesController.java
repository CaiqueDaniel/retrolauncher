package org.retrolauncher.backend.app.games.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.games.application.dtos.GameInfoOutputDto;
import org.retrolauncher.backend.app.games.application.dtos.SaveGameCoverInputDto;
import org.retrolauncher.backend.app.games.application.usecases.CreateGameShortcutUseCase;
import org.retrolauncher.backend.app.games.application.usecases.ListGamesUseCase;
import org.retrolauncher.backend.app.games.application.usecases.SaveGameCoverUseCase;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;

import java.util.List;

public class GamesController {
    private final ListGamesUseCase listGamesUseCase;
    private final SaveGameCoverUseCase saveGameCoverUseCase;

    private final CreateGameShortcutUseCase createGameShortcutUseCase;

    public GamesController(
            ListGamesUseCase listGamesUseCase,
            SaveGameCoverUseCase saveGameCoverUseCase,
            CreateGameShortcutUseCase createGameShortcutUseCase
    ) {
        this.listGamesUseCase = listGamesUseCase;
        this.saveGameCoverUseCase = saveGameCoverUseCase;
        this.createGameShortcutUseCase = createGameShortcutUseCase;
    }

    public List<GameInfoOutputDto> listAll() {
        return this.listGamesUseCase.execute();
    }

    public void saveCover(SaveGameCoverDto dto) {
        this.saveGameCoverUseCase.execute(new SaveGameCoverInputDto(dto.id(), dto.icon()));
    }

    public void createShortcut(String id) {
        this.createGameShortcutUseCase.execute(id);
    }
}
