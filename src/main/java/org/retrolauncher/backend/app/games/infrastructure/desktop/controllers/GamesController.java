package org.retrolauncher.backend.app.games.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.games.application.dtos.GameInfoOutputDto;
import org.retrolauncher.backend.app.games.application.usecases.ListGamesUseCase;

import java.util.List;

public class GamesController {
    private final ListGamesUseCase listGamesUseCase;

    public GamesController(ListGamesUseCase listGamesUseCase) {
        this.listGamesUseCase = listGamesUseCase;
    }

    public List<GameInfoOutputDto> listAll() {
        return this.listGamesUseCase.execute();
    }
}
