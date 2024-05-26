package org.retrolauncher.gui.config;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.retrolauncher.Main;
import org.retrolauncher.gui.base.Page;

import java.io.IOException;
import java.util.Objects;

public class Renderer {
    private Stage stage;
    private static Renderer instance;

    public void render(Page page) throws IOException {
        if (stage != null) stage.close();
        stage = new Stage();
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
