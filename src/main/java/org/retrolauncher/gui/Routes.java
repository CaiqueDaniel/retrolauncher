package org.retrolauncher.gui;

import javafx.stage.Stage;
import org.retrolauncher.gui.views.pages.MainMenuPage;
import org.retrolauncher.gui.views.pages.SetupPage;

public class Routes {
    private final Stage stage;
    private static Routes instance;

    private Routes(Stage stage) {
        this.stage = stage;
    }

    public void switchToHome() {
        new MainMenuPage(this.stage);
    }

    public void switchToSettings() {
        new SetupPage(this.stage);
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
