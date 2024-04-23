package org.retrolauncher.gui.shared.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.retrolauncher.Main;

import java.io.File;
import java.util.Optional;

public class DirectoryField extends VBox {
    @FXML
    private Label label;

    @FXML
    private TextField txtDirPath;

    @FXML
    private Button btnDirPath;

    @FXML
    private Label lblError;

    public DirectoryField() {
        this.load();
    }

    public void setLabel(String text) {
        this.label.setText(text);
    }

    public void setError(String text) {
        this.lblError.setText(text);
        this.lblError.setVisible(true);
    }

    public void setValue(String value) {
        this.txtDirPath.setText(value);
    }

    public Optional<String> getValue() {
        String value = this.txtDirPath.getText();
        return Optional.ofNullable(value.isEmpty() ? null : value);
    }

    private void load() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("shared/templates/components/DirectoryField.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        this.setEventListeners();
    }

    private void setEventListeners() {
        this.txtDirPath.setOnMouseClicked((evt) -> this.lblError.setVisible(false));
        this.btnDirPath.setOnMouseClicked((evt) -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            Optional<File> selectedDir = Optional.ofNullable(directoryChooser.showDialog(this.getScene().getWindow()));

            selectedDir.ifPresent((dir) -> {
                this.txtDirPath.setText(dir.getAbsolutePath());
                this.lblError.setVisible(false);
            });
        });
    }
}
