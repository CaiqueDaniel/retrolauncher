package org.retrolauncher.backend.app.games.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.games.application.dtos.SaveGameCoverInputDto;
import org.retrolauncher.backend.app.games.application.usecases.CreateGameShortcutUseCase;
import org.retrolauncher.backend.app.games.application.usecases.ListGamesUseCase;
import org.retrolauncher.backend.app.games.application.usecases.SaveGameCoverUseCase;
import org.retrolauncher.backend.app.games.application.usecases.UpdateGamesListUseCase;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.ListGameResponse;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;

import java.util.List;

public class GamesController {
    private final ListGamesUseCase listGamesUseCase;
    private final SaveGameCoverUseCase saveGameCoverUseCase;

    private final CreateGameShortcutUseCase createGameShortcutUseCase;
    private final UpdateGamesListUseCase updateGamesListUseCase;

    public GamesController(
            ListGamesUseCase listGamesUseCase,
            SaveGameCoverUseCase saveGameCoverUseCase,
            CreateGameShortcutUseCase createGameShortcutUseCase,
            UpdateGamesListUseCase updateGamesListUseCase
    ) {
        this.listGamesUseCase = listGamesUseCase;
        this.saveGameCoverUseCase = saveGameCoverUseCase;
        this.createGameShortcutUseCase = createGameShortcutUseCase;
        this.updateGamesListUseCase = updateGamesListUseCase;
    }

    public List<ListGameResponse> listAll() {
        return this.listGamesUseCase.execute()
                .stream()
                .map((output) -> new ListGameResponse(
                        output.id(),
                        output.name(),
                        output.platformName(),
                        output.iconPath().orElse(null))).toList();
    }

    public void saveCover(SaveGameCoverDto dto) {
        this.saveGameCoverUseCase.execute(new SaveGameCoverInputDto(dto.id(), dto.icon()));
    }

    public void createShortcut(String id) {
        this.createGameShortcutUseCase.execute(id);
    }

    public void updateList() {
        try {
            this.updateGamesListUseCase.execute();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
