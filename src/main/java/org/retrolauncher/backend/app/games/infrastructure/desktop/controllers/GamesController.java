package org.retrolauncher.backend.app.games.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.games.application.dtos.GameSearchParams;
import org.retrolauncher.backend.app.games.application.dtos.SaveGameCoverInputDto;
import org.retrolauncher.backend.app.games.application.usecases.*;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.ListGameResponse;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.UpdateGameRequestDto;

import java.util.List;
import java.util.UUID;

public class GamesController {
    private final ListGamesUseCase listGamesUseCase;
    private final SaveGameCoverUseCase saveGameCoverUseCase;
    private final CreateGameShortcutUseCase createGameShortcutUseCase;
    private final UpdateGamesListUseCase updateGamesListUseCase;
    private final StartGameUseCase startGameUseCase;
    private final UpdateGameUseCase updateGameUseCase;

    public GamesController(
            ListGamesUseCase listGamesUseCase,
            SaveGameCoverUseCase saveGameCoverUseCase,
            CreateGameShortcutUseCase createGameShortcutUseCase,
            UpdateGamesListUseCase updateGamesListUseCase,
            StartGameUseCase startGameUseCase,
            UpdateGameUseCase updateGameUseCase
    ) {
        this.listGamesUseCase = listGamesUseCase;
        this.saveGameCoverUseCase = saveGameCoverUseCase;
        this.createGameShortcutUseCase = createGameShortcutUseCase;
        this.updateGamesListUseCase = updateGamesListUseCase;
        this.startGameUseCase = startGameUseCase;
        this.updateGameUseCase = updateGameUseCase;
    }

    public List<ListGameResponse> listAll(GameSearchParams params) {
        return this.listGamesUseCase.execute(params)
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

    public void reindexGames() {
        try {
            this.updateGamesListUseCase.execute();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void startGame(UUID id) {
        startGameUseCase.execute(id.toString());
    }

    public void updateGame(UpdateGameRequestDto dto) {
        updateGameUseCase.execute(dto);
    }
}
