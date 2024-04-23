package org.retrolauncher.gui.modules.games.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.ListView;
import org.retrolauncher.Main;
import org.retrolauncher.gui.modules.games.components.GameItem;
import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.events.EventType;
import org.retrolauncher.gui.modules.games.models.Game;
import org.retrolauncher.gui.modules.games.viewmodels.GamesListViewModel;

import java.util.List;

public class GamesList extends ListView<GameItem> {
    private final GamesListViewModel viewModel;

    public GamesList() {
        this.viewModel = new GamesListViewModel();
        this.load();
    }

    private void load() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("modules/games/templates/features/GamesList.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        this.loadGames();

        EventManager.getInstance().subscribe(EventType.FETCH_GAME_LIST, (evt) -> this.loadGames());
    }

    private void loadGames() {
        List<GameItem> gameItemList = this.viewModel.listAll()
                .stream()
                .map((game) -> new GameItem(game, this::onSelectGameItem))
                .toList();
        this.getItems().setAll(gameItemList);
    }

    private void onSelectGameItem(Game game) {
        EventManager.getInstance().notify(EventType.GAME_SELECTED, game);
    }
}
