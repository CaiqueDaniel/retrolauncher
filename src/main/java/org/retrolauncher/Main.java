package org.retrolauncher;

import org.retrolauncher.backend.Backend;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.retrolauncher.backend.config.DependencyInjector;

public class Main extends Application {
    private static final DependencyInjector dependencyInjector = new DependencyInjector();

    public static void main(String[] args) {
        Backend.main(args);
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/pages/MainMenuPage.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("JavaFX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}