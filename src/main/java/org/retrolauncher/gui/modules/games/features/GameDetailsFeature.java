package org.retrolauncher.gui.modules.games.features;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.retrolauncher.Main;
import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.modules.games.components.IGameLabelToInputComponent;
import org.retrolauncher.gui.modules.games.gateways.LocalGamesGateway;
import org.retrolauncher.gui.modules.games.presenters.*;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

public class GameDetailsFeature extends AnchorPane implements IGameDetailsFeature {
    @FXML
    private ImageView imgCover;
    @FXML
    private Label lblPlatformName;
    @FXML
    private IGameLabelToInputComponent lblGameName;
    @FXML
    private Button btnStartGame;
    @FXML
    private Button btnCreateShortcut;
    @FXML
    private VBox paneMain;
    @FXML
    private VBox sectionGameData;
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
        lblGameName.setLabelText(value);
        return this;
    }

    @Override
    public IGameDetailsFeature setLblPlatformName(String value) {
        lblPlatformName.setText(value);
        return this;
    }

    @Override
    public IGameDetailsFeature setImgCover(Path path) {
        final Image image = path == null ?
                new Image(Objects.requireNonNull(Main.class.getResourceAsStream("assets/no-cover.png"))) :
                new Image(path.toUri().toString());
        imgCover.setImage(image);
        return this;
    }

    @Override
    public IGameDetailsFeature showMainPane() {
        paneSelectGame.setVisible(false);
        paneMain.setVisible(true);
        return this;
    }

    @Override
    public IGameDetailsFeature resetBtnShortcut() {
        btnCreateShortcut.setDisable(false);
        btnCreateShortcut.setText("Criar atalho");
        return this;
    }

    @Override
    public IGameDetailsFeature disableBtnShortcutWithLabel(String label) {
        btnCreateShortcut.setDisable(true);
        btnCreateShortcut.setText(label);
        return this;
    }

    @Override
    public String getInputedGameName() {
        return lblGameName.getInputValue();
    }

    @FXML
    private void initialize() {
        imgCover.setOnMouseClicked((evt) -> onClickImageView());
        btnStartGame.setOnMouseClicked((evt) -> presenter.startGame());
        btnCreateShortcut.setOnMouseClicked((evt) -> presenter.createShortcut());
        lblGameName.onClickConfirm(presenter::updateGameName);
    }

    private void onClickImageView() {
        FileChooser fileChooser = new FileChooser();
        File image = fileChooser.showOpenDialog(this.getScene().getWindow());

        if (image == null) return;

        presenter.sendGameCover(image);
    }
}
