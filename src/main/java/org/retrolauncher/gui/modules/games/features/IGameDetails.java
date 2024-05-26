package org.retrolauncher.gui.modules.games.features;

import java.nio.file.Path;

public interface IGameDetails {
    IGameDetails setLblGameName(String value);

    IGameDetails setLblPlatformName(String value);

    IGameDetails setImgCover(Path path);
}
