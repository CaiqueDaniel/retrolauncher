package org.retrolauncher.gui.shared.components;

import javafx.beans.property.StringProperty;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.retrolauncher.Main;

import java.io.File;
import java.util.Optional;

public class DirectoryInput extends VBox {
    @FXML
    private Label label;

    @FXML
    private TextField txtDirPath;

    @FXML
    private Button btnDirPath;

    @FXML
    private Label lblError;

    public DirectoryInput() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("shared/components/DirectoryInput.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public StringProperty textProperty() {
        return label.textProperty();
    }

    public String getText() {
        return label.textProperty().get();
    }

    public void setText(String value) {
        label.textProperty().set(value);
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