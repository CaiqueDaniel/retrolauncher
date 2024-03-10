package org.retrolauncher.backend.app.games.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.games.application.dtos.GameInfoOutputDto;
import org.retrolauncher.backend.app.games.application.dtos.SaveGameCoverInputDto;
import org.retrolauncher.backend.app.games.application.usecases.ListGamesUseCase;
import org.retrolauncher.backend.app.games.application.usecases.SaveGameCoverUseCase;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;

import java.util.List;

public class GamesController {
    private final ListGamesUseCase listGamesUseCase;
    private final SaveGameCoverUseCase saveGameCoverUseCase;

    public GamesController(ListGamesUseCase listGamesUseCase, SaveGameCoverUseCase saveGameCoverUseCase) {
        this.listGamesUseCase = listGamesUseCase;
        this.saveGameCoverUseCase = saveGameCoverUseCase;
    }

    public List<GameInfoOutputDto> listAll() {
        return this.listGamesUseCase.execute();
    }

    public void saveCover(SaveGameCoverDto dto) {
        this.saveGameCoverUseCase.execute(new SaveGameCoverInputDto(dto.id(), dto.icon()));
    }
}
