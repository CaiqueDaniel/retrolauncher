package org.retrolauncher.gui.modules.games.presenters;

import org.retrolauncher.gui.modules.games.models.Game;

public interface ListGamesPresenter {
    void listAll();

    void selectGameItem(Game game);

    void reindexGames();
}
