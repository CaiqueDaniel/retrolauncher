package org.retrolauncher.gui.views.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.retrolauncher.Main;
import org.retrolauncher.gui.contexts.SelectedGameContext;
import org.retrolauncher.gui.gateways.GamesGateway;
import org.retrolauncher.gui.models.Game;
import org.retrolauncher.gui.viewmodels.CoverViewViewModel;

import java.io.File;

public class CoverView extends Pane {
    @FXML
    private Label selectGameLbl;

    @FXML
    private Button btnAddCover;

    @FXML
    private ImageView imgGameCover;

    @FXML
    private Pane gameCoverPane;

    private Game selectedGame;

    private CoverViewViewModel coverViewViewModel;

    public CoverView() {
        this.coverViewViewModel = new CoverViewViewModel(new GamesGateway());
        this.load();
    }

    private void load() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/features/CoverView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        SelectedGameContext.subscribe((game) -> {
            this.selectGameLbl.setVisible(false);
            this.gameCoverPane.setVisible(true);
            this.selectedGame = game;
        });

        this.setEventListeners();
    }

    private void setEventListeners() {
        this.btnAddCover.setOnMouseClicked((evt) -> {
            FileChooser fileChooser = new FileChooser();
            File image = fileChooser.showOpenDialog(this.getScene().getWindow());
            this.imgGameCover.setImage(new Image(image.toURI().toString()));
            this.coverViewViewModel.saveCover(this.selectedGame.id(), image);
        });
    }
}
