package org.retrolauncher.gui.modules.games.presenters;

import org.retrolauncher.gui.events.*;
import org.retrolauncher.gui.modules.games.features.IListGamesFeature;
import org.retrolauncher.gui.modules.games.gateways.GamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;

import java.util.*;

public class DefaultListGamesPresenter implements ListGamesPresenter {
    private final IListGamesFeature view;
    private final GamesGateway gateway;
    private final EventManager eventManager;
    private List<Game> games = new ArrayList<>();

    public DefaultListGamesPresenter(IListGamesFeature view, GamesGateway gateway, EventManager eventManager) {
        this.view = view;
        this.gateway = gateway;
        this.eventManager = eventManager;

        registerListeners();
    }

    @Override
    public void listAll() {
        games = gateway.listAll();
        view.updateList(games);
    }

    public void selectGameItem(Game game) {
        eventManager.notify(EventType.GAME_SELECTED, game);
    }

    public void reindexGames() {
        gateway.reindexGames();
        listAll();
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
            game.getIconPath().ifPresent((path) -> foundGame.replaceIcon(path.toFile()));
        });
        view.updateList(games);
    }
}
