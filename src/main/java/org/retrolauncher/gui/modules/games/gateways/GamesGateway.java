package org.retrolauncher.gui.modules.games.gateways;

import org.retrolauncher.gui.modules.games.models.Game;

import java.util.List;

public interface GamesGateway {
    List<Game> listAll();

    void updateGame(Game game);

    void createShortcut(Game game);

    void reindexGames();
}
