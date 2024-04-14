package org.retrolauncher.gui.views.pages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
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

    private final Stage stage;
    private final SetupViewModel setupViewModel;

    public MainMenuPage(Stage stage) {
        this.stage = stage;
        this.setupViewModel = new SetupViewModel();

        this.load();
    }

    private void load() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/pages/MainMenuPage.fxml"));
            loader.setController(this);
            loader.load();

            this.stage.setResizable(true);
            this.stage.setScene(new Scene(loader.getRoot()));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
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
}
