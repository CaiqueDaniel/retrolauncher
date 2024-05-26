package org.retrolauncher.gui.shared.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.shared.presenters.DefaultNavbarPresenter;
import org.retrolauncher.gui.shared.presenters.NavbarPresenter;

import java.io.IOException;

public class Navbar extends HBox {
    @FXML
    private Button btnReindexGamesList;

    private final NavbarPresenter presenter;

    public Navbar() {
        presenter = new DefaultNavbarPresenter(EventManager.getInstance());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("shared/features/Navbar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize() {
        btnReindexGamesList.setOnMouseClicked((evt) -> presenter.reindexGamesList());
    }
}
