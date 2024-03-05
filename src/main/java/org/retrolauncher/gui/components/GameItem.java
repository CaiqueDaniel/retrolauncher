package org.retrolauncher.gui.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.models.Game;

public class GameItem extends HBox {
    private final Game game;
    @FXML()
    private Label label;

    public GameItem(Game game) {
        this.game = game;
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

    private void setEventListeners(){
        this.setOnMouseClicked((evt)->{

        });
    }
}
