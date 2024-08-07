package org.retrolauncher.gui.modules.games.features;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.modules.games.components.GameItem;
import org.retrolauncher.gui.modules.games.gateways.LocalGamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;
import org.retrolauncher.gui.modules.games.presenters.*;

import java.io.IOException;
import java.util.List;

public class ListGamesFeature extends VBox implements IListGamesFeature {
    @FXML
    private ListView<GameItem> lvGames;
    @FXML
    private Button btnReindexGames;
    @FXML
    private TextField txtSearch;
    private final ListGamesPresenter presenter;

    public ListGamesFeature() {
        this.presenter = new DefaultListGamesPresenter(this, new LocalGamesGateway(), EventManager.getInstance());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modules/games/features/ListGamesFeature.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        presenter.listAll();
        btnReindexGames.setOnMouseClicked((evt) -> presenter.reindexGames());
        txtSearch.setOnKeyTyped((evt) -> presenter.setGameNameFilter(txtSearch.getText()));
    }

    @Override
    public void updateList(List<Game> games) {
        lvGames.getItems().clear();
        games.forEach((game) -> lvGames.getItems().add(new GameItem(game, presenter::selectGameItem)));
    }

    @Override
    public void setIsLoadingReindexGamesBtn(boolean isLoading) {
        btnReindexGames.setDisable(isLoading);
        if (isLoading) {
            btnReindexGames.setText("Reindexando...");
            return;
        }
        btnReindexGames.setText("Reindexar Jogos");
    }
}
