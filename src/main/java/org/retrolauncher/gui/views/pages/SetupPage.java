package org.retrolauncher.gui.views.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.retrolauncher.Main;

public class SetupPage {
    private final Stage stage;

    public SetupPage(Stage stage) {
        this.stage = stage;
        this.load();
    }

    private void load() {
        try {
            this.stage.hide();
            this.stage.setResizable(false);

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/pages/SetupPage.fxml"));
            loader.setController(this);
            loader.load();

            this.stage.setScene(new Scene(loader.getRoot()));
            this.stage.show();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
