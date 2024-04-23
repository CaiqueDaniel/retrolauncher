package org.retrolauncher.gui.modules.settings.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.retrolauncher.Main;

public class SetupPage {

    public static Scene createScene() {
        return new Scene(SetupPage.load());
    }

    private static Parent load() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("modules/settings/templates/pages/SetupPage.fxml"));
            loader.setController(new SetupPage());
            loader.load();
            return loader.getRoot();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}