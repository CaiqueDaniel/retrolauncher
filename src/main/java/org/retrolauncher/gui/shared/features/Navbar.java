package org.retrolauncher.gui.shared.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.router.*;

import java.io.IOException;

public class Navbar extends HBox {
    @FXML
    private Button btnGames, btnSetting;

    public Navbar() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("shared/features/Navbar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize() {
        btnGames.setOnMouseClicked((evt) -> Router.getInstance().navigateTo(Routes.GAMES));
        btnSetting.setOnMouseClicked((evt) -> Router.getInstance().navigateTo(Routes.SETTINGS));
    }
}
