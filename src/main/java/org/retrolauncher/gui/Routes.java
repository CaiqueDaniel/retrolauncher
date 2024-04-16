package org.retrolauncher.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.retrolauncher.gui.views.pages.MainMenuPage;
import org.retrolauncher.gui.views.pages.SetupPage;

public class Routes {
    private final Stage stage;
    private static Routes instance;

    private final Scene mainMenuPage;
    private final Scene setupPage;

    private Routes(Stage stage) {
        this.stage = stage;
        this.mainMenuPage = MainMenuPage.createScene();
        this.setupPage = SetupPage.createScene();
    }

    public void switchToHome() {
        stage.setScene(this.mainMenuPage);
    }

    public void switchToSettings() {
        stage.setScene(this.setupPage);
    }

    public static void initialize(Stage stage) {
        Routes.instance = new Routes(stage);
    }

    public static Routes getInstance() {
        if (Routes.instance == null)
            throw new RuntimeException("Routes was not initialized. Use initialize method.");
        return Routes.instance;
    }
}
