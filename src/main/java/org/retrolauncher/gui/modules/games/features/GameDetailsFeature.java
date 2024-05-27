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

public class GameDetailsFeature extends AnchorPane implements IGameDetailsFeature {
    @FXML
    private ImageView imgCover;
    @FXML
    private Label lblGameName, lblPlatformName;
    @FXML
    private Button btnStartGame, btnCreateShortcut;
    @FXML
    private VBox paneMain;
    @FXML
    private FlowPane paneSelectGame;

    private final GameDetailsPresenter presenter;

    public GameDetailsFeature() {
        presenter = new DefaultGameDetailsPresenter(this, EventManager.getInstance(), new LocalGamesGateway());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modules/games/features/GameDetailsFeature.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public IGameDetailsFeature setLblGameName(String value) {
        lblGameName.setText(value);
        return this;
    }

    @Override
    public IGameDetailsFeature setLblPlatformName(String value) {
        lblPlatformName.setText(value);
        return this;
    }

    @Override
    public IGameDetailsFeature setImgCover(Path path) {
        imgCover.setImage(new Image(path.toUri().toString()));
        return this;
    }

    @Override
    public IGameDetailsFeature showMainPane() {
        paneSelectGame.setVisible(false);
        paneMain.setVisible(true);
        return this;
    }

    @FXML
    private void initialize() {
        imgCover.setOnMouseClicked((evt) -> onClickImageView());
        btnStartGame.setOnMouseClicked((evt) -> presenter.startGame());
        btnCreateShortcut.setOnMouseClicked((evt) -> presenter.createShortcut());
    }

    private void onClickImageView() {
        FileChooser fileChooser = new FileChooser();
        File image = fileChooser.showOpenDialog(this.getScene().getWindow());

        if (image == null) return;

        presenter.sendGameCover(image);
    }
}
