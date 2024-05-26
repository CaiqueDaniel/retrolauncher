package org.retrolauncher.gui.modules.games.features;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.retrolauncher.Main;
import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.modules.games.gateways.LocalGamesGateway;
import org.retrolauncher.gui.modules.games.presenters.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class GameDetails extends AnchorPane implements IGameDetails {
    @FXML
    private ImageView imgCover;
    @FXML
    private Label lblGameName, lblPlatformName;
    @FXML
    private Button btnCreateShortcut;
    @FXML
    private VBox paneMain;
    @FXML
    private FlowPane paneSelectGame;

    private final GameDetailsPresenter presenter;

    public GameDetails() {
        presenter = new DefaultGameDetailsPresenter(this, EventManager.getInstance(), new LocalGamesGateway());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modules/games/features/GameDetails.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public IGameDetails setLblGameName(String value) {
        lblGameName.setText(value);
        return this;
    }

    @Override
    public IGameDetails setLblPlatformName(String value) {
        lblPlatformName.setText(value);
        return this;
    }

    @Override
    public IGameDetails setImgCover(Path path) {
        imgCover.setImage(new Image(path.toUri().toString()));
        return this;
    }

    @Override
    public IGameDetails showMainPane() {
        paneSelectGame.setVisible(false);
        paneMain.setVisible(true);
        return this;
    }

    @FXML
    private void initialize() {
        imgCover.setOnMouseClicked((evt) -> onClickImageView());
        btnCreateShortcut.setOnMouseClicked((evt) -> presenter.createShortcut());
    }

    private void onClickImageView() {
        FileChooser fileChooser = new FileChooser();
        File image = fileChooser.showOpenDialog(this.getScene().getWindow());

        if (image == null) return;

        presenter.sendGameCover(image);
    }
}
