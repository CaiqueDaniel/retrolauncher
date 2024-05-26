package org.retrolauncher.gui.shared.features;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import org.retrolauncher.Main;

import java.io.IOException;

public class Navbar extends HBox {
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
}
