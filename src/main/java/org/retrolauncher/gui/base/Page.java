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
        final Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(template));
        fxmlLoader.setController(this);

        final Scene scene = new Scene(fxmlLoader.load(), screenSize.getWidth(), screenSize.getHeight());
        final String style = Objects.requireNonNull(Main.class.getResource("styles/button.css")).toExternalForm();
        scene.getStylesheets().add(style);

        return scene;
    }
}
