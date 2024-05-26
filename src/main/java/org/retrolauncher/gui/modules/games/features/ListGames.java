package org.retrolauncher.gui.modules.games.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import org.retrolauncher.Main;
import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.modules.games.components.GameItem;
import org.retrolauncher.gui.modules.games.gateways.LocalGamesGateway;
import org.retrolauncher.gui.modules.games.models.Game;
import org.retrolauncher.gui.modules.games.presenters.DefaultListGamesPresenter;
import org.retrolauncher.gui.modules.games.presenters.ListGamesPresenter;

import java.io.IOException;
import java.util.List;

public class ListGames extends ListView<GameItem> implements IListGames {
    private final ListGamesPresenter presenter;

    public ListGames() {
        this.presenter = new DefaultListGamesPresenter(this, new LocalGamesGateway(), EventManager.getInstance());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modules/games/features/ListGames.fxml"));
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
        this.presenter.listAll();
    }

    @Override
    public void updateList(List<Game> games) {
        getItems().clear();
        games.forEach((game) -> getItems().add(new GameItem(game, presenter::onSelectGameItem)));
    }
}
