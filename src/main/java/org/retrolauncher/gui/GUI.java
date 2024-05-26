package org.retrolauncher.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.retrolauncher.gui.router.Router;
import org.retrolauncher.gui.router.Routes;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {
        Router.getInstance().navigateTo(Routes.GAMES);
    }
}
