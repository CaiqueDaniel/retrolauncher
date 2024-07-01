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
    private Game selectedGame;

    public DefaultGameDetailsPresenter(IGameDetailsFeature view, EventManager eventManager, GamesGateway gateway) {
        this.view = view;
        this.eventManager = eventManager;
        this.gateway = gateway;

        this.registerListeners();
    }

    @Override
    public void sendGameCover(File cover) {
        final Game game = new Game(selectedGame);
        game.replaceIcon(cover);
        gateway.saveCover(game).thenAccept((r) -> Platform.runLater(() -> {
            if (selectedGame.getId().equals(game.getId()))
                view.setImgCover(cover.toPath());
            eventManager.notify(EventType.GAME_UPDATED, game);
        }));
    }

    @Override
    public void updateGameName() {
        selectedGame.setName(view.getInputedGameName());
        gateway.updateGame(selectedGame).thenAccept((r) -> Platform.runLater(() -> {
            eventManager.notify(EventType.GAME_UPDATED, selectedGame);
            eventManager.notify(EventType.GAME_SELECTED, selectedGame);
        }));
    }

    @Override
    public void createShortcut() {
        gateway.createShortcut(selectedGame);
        view.disableBtnShortcutWithLabel("Atalho criado");
    }

    @Override
    public void startGame() {
        gateway.startGame(selectedGame);
    }

    private void registerListeners() {
        eventManager.subscribe(EventType.GAME_SELECTED, (e) -> e.ifPresent((game) -> this.updateGame((Game) game)));
    }

    private void updateGame(Game game) {
        this.selectedGame = new Game(game);
        this.updateView();
    }

    private void updateView() {
        view.setLblGameName(selectedGame.getName())
                .setLblPlatformName(selectedGame.getPlatformName())
                .setImgCover(selectedGame.getIconPath().orElse(null))
                .resetBtnShortcut()
                .showMainPane();
    }
}
