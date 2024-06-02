package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.ListGameResponse;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.UpdateGameRequestDto;

import java.util.List;
import java.util.UUID;

public interface GamesFacade {
    List<ListGameResponse> listAll();

    void saveCover(SaveGameCoverDto dto);

    void createShortcut(String id);

    void reindexGames();

    void startGame(UUID id);

    void updateGame(UpdateGameRequestDto dto) throws RuntimeException;
}
