package org.retrolauncher.gui.modules.games.presenters;

import org.retrolauncher.gui.modules.games.models.Game;

public interface ListGamesPresenter {
    void listAll();

    void onSelectGameItem(Game game);
}
