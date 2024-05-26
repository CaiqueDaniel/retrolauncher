package org.retrolauncher.gui.modules.games.presenters;

import org.retrolauncher.Main;
import org.retrolauncher.gui.events.*;
import org.retrolauncher.gui.modules.games.features.IGameDetails;
import org.retrolauncher.gui.modules.games.models.Game;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class DefaultGameDetailsPresenter implements GameDetailsPresenter {
    private final IGameDetails view;
    private final EventManager eventManager;

    public DefaultGameDetailsPresenter(IGameDetails view, EventManager eventManager) {
        this.view = view;
        this.eventManager = eventManager;

        this.registerListeners();
    }

    private void registerListeners() {
        eventManager.subscribe(EventType.GAME_SELECTED, (e) -> e.ifPresent((game) -> this.updateView((Game) game)));
    }

    private void updateView(Game game) {

        final Path NO_COVER_RESOURCE = new File(Main.class.getResource("assets/no-cover.png").getFile()).toPath();
        System.out.println(NO_COVER_RESOURCE);
        view.setLblGameName(game.getName())
                .setLblPlatformName(game.getPlatformName())
                .setImgCover(game.getIconPath().orElse(NO_COVER_RESOURCE));
    }
}
