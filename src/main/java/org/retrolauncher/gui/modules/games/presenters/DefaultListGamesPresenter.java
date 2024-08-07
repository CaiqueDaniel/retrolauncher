package org.retrolauncher.gui.modules.games.presenters;

import javafx.application.Platform;
import org.retrolauncher.gui.events.*;
import org.retrolauncher.gui.modules.games.dtos.GameSearchFilter;
import org.retrolauncher.gui.modules.games.features.IListGamesFeature;
import org.retrolauncher.gui.modules.games.gateways.GamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;

import java.util.*;

public class DefaultListGamesPresenter implements ListGamesPresenter {
    private final IListGamesFeature view;
    private final GamesGateway gateway;
    private final EventManager eventManager;
    private final GameSearchFilter filter = new GameSearchFilter();
    private List<Game> games = new ArrayList<>();

    public DefaultListGamesPresenter(IListGamesFeature view, GamesGateway gateway, EventManager eventManager) {
        this.view = view;
        this.gateway = gateway;
        this.eventManager = eventManager;

        registerListeners();
    }

    @Override
    public void listAll() {
        gateway.listAll(this.filter).thenAccept((games) -> {
            this.games = games;
            Platform.runLater(() -> view.updateList(games));
        });
    }

    public void selectGameItem(Game game) {
        eventManager.notify(EventType.GAME_SELECTED, game);
    }

    public void reindexGames() {
        view.setIsLoadingReindexGamesBtn(true);
        gateway.reindexGames()
                .thenAccept((r) -> listAll())
                .whenComplete((r, e) -> Platform.runLater(() -> view.setIsLoadingReindexGamesBtn(false)));
    }

    @Override
    public void setGameNameFilter(String value) {
        this.filter.name(value);
        this.listAll();
    }

    private void registerListeners() {
        eventManager.subscribe(EventType.GAME_UPDATED, (evt) -> evt.ifPresent((game) -> onGameUpdated((Game) game)));
    }

    private void onGameUpdated(Game game) {
        final Optional<Game> result = games.stream()
                .filter((oldGame) -> oldGame.getId().equals(game.getId()))
                .findFirst();
        result.ifPresent((foundGame) -> {
            foundGame.setName(game.getName());
            game.getIconPath().ifPresent((path) -> {
                foundGame.replaceIcon(path.toFile());
            });
        });
        view.updateList(games);
    }
}
