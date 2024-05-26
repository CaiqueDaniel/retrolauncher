package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.ListGameResponse;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.SaveGameCoverDto;

import java.util.List;

public interface GamesFacade {
    List<ListGameResponse>  listAll();

    void saveCover(SaveGameCoverDto dto);

    void createShortcut(String id);

    void reindexGames();
}
