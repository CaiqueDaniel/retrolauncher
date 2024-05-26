package org.retrolauncher.gui.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.retrolauncher.Main;

import java.io.IOException;

public abstract class Page {
    private final String template;

    protected Page(String template) {
        this.template = template;
    }

    public Scene createScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(template));
        fxmlLoader.setController(this);
        return new Scene(fxmlLoader.load());
    }
}
