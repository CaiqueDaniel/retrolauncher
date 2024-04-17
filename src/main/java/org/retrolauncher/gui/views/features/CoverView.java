package org.retrolauncher.gui.views.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.retrolauncher.Main;
import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.events.EventType;
import org.retrolauncher.gui.gateways.GamesGateway;
import org.retrolauncher.gui.models.Game;
import org.retrolauncher.gui.viewmodels.CoverViewViewModel;

import java.io.File;
import java.util.Optional;

public class CoverView extends AnchorPane {
    @FXML
    private Label selectGameLbl;

    @FXML
    private Button btnAddCover;

    @FXML
    private Button btnAddShortcut;

    @FXML
    private ImageView imgGameCover;

    @FXML
    private VBox gameCoverPane;

    private Game selectedGame;

    private final CoverViewViewModel coverViewViewModel;

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
        final String NO_COVER_RESOURCE = "assets/no-cover.png";

        EventManager.getInstance().subscribe(EventType.GAME_SELECTED, (evt) -> {
            if (evt.isEmpty())
                return;

            this.selectedGame = (Game) evt.get();
            this.selectGameLbl.setVisible(false);
            this.gameCoverPane.setVisible(true);
            handleClickedShortcutBtn();

            if (this.selectedGame.iconPath().isPresent()) {
                this.btnAddCover.setText("Alterar capa");
                this.imgGameCover.setImage(new Image(this.selectedGame.iconPath().get().toUri().toString()));
            } else {
                this.btnAddCover.setText("Adicionar capa");
                Optional.ofNullable(Main.class.getResourceAsStream(NO_COVER_RESOURCE))
                        .ifPresent((stream) -> this.imgGameCover.setImage(new Image(stream)));
            }
        });

        this.setEventListeners();
    }

    private void setEventListeners() {
        this.btnAddCover.setOnMouseClicked((evt) -> {
            FileChooser fileChooser = new FileChooser();
            File image = fileChooser.showOpenDialog(this.getScene().getWindow());

            if (image == null)
                return;

            coverViewViewModel.saveCover(this.selectedGame.id(), image);
            btnAddCover.setText("Alterar capa");
            EventManager.getInstance().notify(EventType.FETCH_GAME_LIST);
        });

        this.btnAddShortcut.setOnMouseClicked((evt) -> {
            coverViewViewModel.createShortcut(this.selectedGame.id());
            handleResetShortcutBtn();
        });
    }

    private void handleClickedShortcutBtn() {
        btnAddShortcut.setDisable(false);
        btnAddShortcut.setText("Adicionar atalho");
    }

    private void handleResetShortcutBtn() {
        btnAddShortcut.setText("Atalho adicionado");
        btnAddShortcut.setDisable(true);
    }
}
