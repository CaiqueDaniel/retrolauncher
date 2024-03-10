package org.retrolauncher.gui.views.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.ListView;
import org.retrolauncher.Main;
import org.retrolauncher.gui.components.GameItem;
import org.retrolauncher.gui.contexts.SelectedGameContext;
import org.retrolauncher.gui.models.Game;
import org.retrolauncher.gui.viewmodels.GamesListViewModel;

import java.util.List;

public class GamesList extends ListView<GameItem> {

    public GamesList() {
        this.load();
    }

    private void load() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/features/GamesList.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        GamesListViewModel viewModel = new GamesListViewModel();
        List<GameItem> gameItemList = viewModel.listAll()
                .stream()
                .map((game) -> new GameItem(game, this::onSelectGameItem))
                .toList();
        this.getItems().addAll(gameItemList);
    }

    private void onSelectGameItem(Game game) {
        SelectedGameContext.dispatch(game);
    }
}
