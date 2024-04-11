package org.retrolauncher.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        Routes.initialize(stage);
        Routes.getInstance().switchToSettings();

        stage.setTitle("Retro Launcher");
        stage.show();
    }
}
