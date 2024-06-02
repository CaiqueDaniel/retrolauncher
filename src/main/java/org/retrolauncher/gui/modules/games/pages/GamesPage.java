package org.retrolauncher.gui.modules.games.pages;

import javafx.scene.Scene;
import org.retrolauncher.Main;
import org.retrolauncher.gui.base.Page;

import java.io.IOException;
import java.util.Objects;

public class GamesPage extends Page {
    public GamesPage() {
        super("modules/games/pages/GamesPage.fxml");
    }

    @Override
    public Scene createScene() throws IOException {
        final Scene scene = super.createScene();
        final String style = Objects.requireNonNull(Main.class.getResource("styles/list-games.css")).toExternalForm();
        scene.getStylesheets().add(style);
        return scene;
    }
}
