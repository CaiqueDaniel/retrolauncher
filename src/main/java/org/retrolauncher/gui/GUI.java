package org.retrolauncher.gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.retrolauncher.Main;

import java.util.Objects;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        Routes.initialize(stage);
        Routes.getInstance().switchToHome();

        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/icon.png"))));
        stage.setTitle("Retro Launcher");
        stage.show();
    }
}
