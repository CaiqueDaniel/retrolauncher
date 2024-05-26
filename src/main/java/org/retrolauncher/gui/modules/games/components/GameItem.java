package org.retrolauncher.gui.modules.games.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.modules.games.models.Game;

import java.util.function.Consumer;

public class GameItem extends HBox {
    private final Game game;
    private final Consumer<Game> onClick;
    @FXML()
    private Label label;

    public GameItem(Game game, Consumer<Game> onClick) {
        this.game = game;
        this.onClick = onClick;
        this.load();
    }

    private void load() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modules/games/components/GameItem.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        this.label.setText(game.getName() + " (" + game.getPlatformName() + ")");
        this.setEventListeners();
    }

    private void setEventListeners() {
        this.setOnMouseClicked((evt) -> onClick.accept(game));
    }
}
