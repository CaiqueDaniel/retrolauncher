package org.retrolauncher.gui.views.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.ListView;
import org.retrolauncher.Main;
import org.retrolauncher.gui.components.GameItem;
import org.retrolauncher.gui.viewmodels.GamesListViewModel;

import java.util.List;

public class GamesList extends ListView<GameItem> {

    public GamesList(){
        this.load();
    }

    @FXML
    public void initialize() {
        GamesListViewModel viewModel = new GamesListViewModel();
        List<GameItem> gameItemList = viewModel.listAll().stream().map(GameItem::new).toList();
        this.getItems().addAll(gameItemList);
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
}
