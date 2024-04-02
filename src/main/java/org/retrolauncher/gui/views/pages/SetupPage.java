package org.retrolauncher.gui.views.pages;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class SetupPage {
    @FXML
    private TextField txtRomsPath;

    @FXML
    private TextField txtRetroarchPath;

    @FXML
    private Button btnRomsPath;

    @FXML
    private Button btnRetroarchPath;
    private File selectedDir;

    @FXML
    private void initialize() {
        this.setEventListeners();
    }

    private void setEventListeners() {
        this.btnRomsPath.setOnMouseClicked((evt) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File selectedDir = directoryChooser.showDialog(this.btnRomsPath.getScene().getWindow());

            if (selectedDir != null)
                this.txtRomsPath.setText(selectedDir.getAbsolutePath());
        });
    }
}
