package org.retrolauncher.gui.modules.games.presenters;

import javafx.application.Platform;
import org.retrolauncher.gui.events.*;
import org.retrolauncher.gui.modules.games.features.IGameDetailsFeature;
import org.retrolauncher.gui.modules.games.gateways.GamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;

import java.io.File;

public class DefaultGameDetailsPresenter implements GameDetailsPresenter {
    private final IGameDetailsFeature view;
    private final EventManager eventManager;
    private final GamesGateway gateway;
    private Game game;

    public DefaultGameDetailsPresenter(IGameDetailsFeature view, EventManager eventManager, GamesGateway gateway) {
        this.view = view;
        this.eventManager = eventManager;
        this.gateway = gateway;

        this.registerListeners();
    }

    @Override
    public void sendGameCover(File cover) {
        game.replaceIcon(cover);
        gateway.saveCover(game);
    }

    @Override
    public void updateGameName() {
        game.setName(view.getInputedGameName());
        gateway.updateGame(game)
                .thenAccept((r) -> Platform.runLater(() -> {
                    eventManager.notify(EventType.GAME_UPDATED, game);
                    eventManager.notify(EventType.GAME_SELECTED, game);
                }));
    }

    @Override
    public void createShortcut() {
        gateway.createShortcut(game);
        view.disableBtnShortcutWithLabel("Atalho criado");
    }

    @Override
    public void startGame() {
        gateway.startGame(game);
    }

    private void registerListeners() {
        eventManager.subscribe(EventType.GAME_SELECTED, (e) -> e.ifPresent((game) -> this.updateGame((Game) game)));
    }

    private void updateGame(Game game) {
        this.game = new Game(game);
        this.updateView();
    }

    private void updateView() {
        view.setLblGameName(game.getName())
                .setLblPlatformName(game.getPlatformName())
                .setImgCover(game.getIconPath().orElse(null))
                .resetBtnShortcut()
                .showMainPane();
    }
}
