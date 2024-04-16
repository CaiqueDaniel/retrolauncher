package org.retrolauncher.gui.views.pages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.FlowPane;
import org.retrolauncher.Main;
import org.retrolauncher.gui.Routes;
import org.retrolauncher.gui.models.Setting;
import org.retrolauncher.gui.viewmodels.SetupViewModel;

import java.util.Optional;

public class MainMenuPage {
    @FXML
    private FlowPane noSettingsPane;

    @FXML
    private SplitPane mainPane;

    @FXML
    private Button btnGotoSettings;
    private final SetupViewModel setupViewModel;

    private MainMenuPage() {
        this.setupViewModel = new SetupViewModel();
    }

    @FXML
    private void initialize() {
        Optional<Setting> settings = this.setupViewModel.get();

        if (settings.isEmpty()) {
            this.mainPane.setVisible(false);
            this.noSettingsPane.setVisible(true);
        }

        this.addEventListeners();
    }

    private void addEventListeners() {
        this.btnGotoSettings.setOnMouseClicked((evt) -> {
            Routes.getInstance().switchToSettings();
        });
    }

    public static Scene createScene() {
        return new Scene(MainMenuPage.load(new MainMenuPage()));
    }

    private static Parent load(MainMenuPage instance) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/pages/MainMenuPage.fxml"));
            loader.setController(instance);
            loader.load();
            return loader.getRoot();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
