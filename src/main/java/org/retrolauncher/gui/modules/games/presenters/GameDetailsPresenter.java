package org.retrolauncher.gui.modules.games.presenters;

import java.io.File;

public interface GameDetailsPresenter {
    void sendGameCover(File cover);
    void createShortcut();
}
