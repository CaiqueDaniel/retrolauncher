package org.retrolauncher.gui.modules.games.features;

import org.retrolauncher.gui.modules.games.models.Game;

import java.util.List;

public interface IListGamesFeature {
    void updateList(List<Game> games);

    void setIsLoadingReindexGamesBtn(boolean isLoading);
}
