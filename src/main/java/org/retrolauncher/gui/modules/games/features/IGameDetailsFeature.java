package org.retrolauncher.gui.modules.games.features;

import java.nio.file.Path;

public interface IGameDetailsFeature {
    IGameDetailsFeature setLblGameName(String value);

    IGameDetailsFeature setLblPlatformName(String value);

    IGameDetailsFeature setImgCover(Path path);

    IGameDetailsFeature showMainPane();
}
