package org.retrolauncher.gui.modules.games.gateways;

import org.retrolauncher.gui.modules.games.models.Game;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GamesGateway {
    List<Game> listAll();

    CompletableFuture<Void> updateGame(Game game);

    CompletableFuture<Void> saveCover(Game game);

    void createShortcut(Game game);

    void reindexGames();

    void startGame(Game game);
}
