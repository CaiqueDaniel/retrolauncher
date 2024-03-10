package org.retrolauncher.gui.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.models.Game;

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
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("components/GameItem.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            this.getStylesheets().add(Main.class.getResource("styles/game-item.css").toExternalForm());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        this.label.setText(this.game.name());
        this.setEventListeners();
    }

    private void setEventListeners() {
        this.setOnMouseClicked((evt) -> this.onClick.accept(this.game));
    }
}
