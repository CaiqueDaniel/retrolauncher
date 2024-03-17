package org.retrolauncher.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.retrolauncher.Main;

public class GUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("views/pages/MainMenuPage.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Retro Launcher");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
