package org.retrolauncher.gui.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.retrolauncher.Main;

import java.io.IOException;
import java.util.Objects;

public abstract class Page {
    private final String template;

    protected Page(String template) {
        this.template = template;
    }

    public Scene createScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(template));
        fxmlLoader.setController(this);

        Scene scene = new Scene(fxmlLoader.load());
        String style = Objects.requireNonNull(Main.class.getResource("styles/button.css")).toExternalForm();
        scene.getStylesheets().add(style);

        return scene;
    }
}
