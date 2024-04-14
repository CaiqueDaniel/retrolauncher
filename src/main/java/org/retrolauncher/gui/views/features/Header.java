package org.retrolauncher.gui.views.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.Routes;
import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.events.EventType;
import org.retrolauncher.gui.viewmodels.HeaderViewModel;

public class Header extends HBox {
    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnSettings;

    private final HeaderViewModel viewModel;

    public Header() {
        this.viewModel = new HeaderViewModel();
        this.load();
    }

    private void load() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/features/Header.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        this.addEventListeners();
    }

    private void addEventListeners() {
        btnUpdate.setOnMouseClicked((evt) -> {
            btnUpdate.setDisable(true);
            btnUpdate.setText("Atualizando");
            viewModel.updateList();
            btnUpdate.setDisable(false);
            btnUpdate.setText("Atualizar");

            EventManager.getInstance().notify(EventType.FETCH_GAME_LIST);
        });

        btnSettings.setOnMouseClicked((evt) -> Routes.getInstance().switchToSettings());
    }
}
