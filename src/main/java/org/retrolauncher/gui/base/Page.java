package org.retrolauncher.gui.base;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import org.retrolauncher.Main;

import java.io.IOException;
import java.util.Objects;

public abstract class Page {
    private final String template;

    protected Page(String template) {
        this.template = template;
    }

    public Scene createScene() throws IOException {
        final var screenSize = Screen.getPrimary().getVisualBounds();
        final var fxmlLoader = new FXMLLoader(Main.class.getResource(template));
        final var scene = new Scene(fxmlLoader.load(), screenSize.getWidth(), screenSize.getHeight());

        fxmlLoader.setController(this);
        scene.getStylesheets()
                .add(Objects.requireNonNull(Main.class.getResource("styles/button.css")).toExternalForm());
        scene.getStylesheets()
                .add(Objects.requireNonNull(Main.class.getResource("styles/text-field.css")).toExternalForm());

        return scene;
    }
}
