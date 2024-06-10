package org.retrolauncher.gui.config;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.retrolauncher.Main;
import org.retrolauncher.gui.base.Page;
import org.retrolauncher.gui.events.EventManager;

import java.io.IOException;
import java.util.Objects;

public class Renderer {
    private final Stage stage = new Stage();
    private static Renderer instance;

    private Renderer() {
        stage.setMaximized(true);
    }

    public void render(Page page) throws IOException {
        EventManager.getInstance().clear();
        stage.setScene(page.createScene());
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/icon.png"))));
        stage.setTitle("Retro Launcher");
        stage.show();
    }

    public static Renderer getInstance() {
        if (instance == null)
            instance = new Renderer();
        return instance;
    }
}
