package org.retrolauncher.gui.modules.games.components;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.retrolauncher.Main;

public class GameLabelToInputComponent extends VBox implements IGameLabelToInputComponent {
    @FXML
    private Label label;

    @FXML
    private TextField textField;

    @FXML
    private HBox controls;

    @FXML
    private Button btnOk, btnCancel;

    public GameLabelToInputComponent() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modules/games/components/GameLabelToInputComponent.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public Font getFont() {
        return label.fontProperty().get();
    }

    public void setFont(Font value) {
        label.fontProperty().set(value);
    }

    public String getPlaceholder() {
        return textField.promptTextProperty().get();
    }

    public void setPlaceholder(String value) {
        textField.promptTextProperty().set(value);
    }

    @Override
    public String getInputValue() {
        return textField.getText();
    }

    @Override
    public void setLabelText(String value) {
        label.setText(value);
    }

    @Override
    public void onClickConfirm(Runnable callback) {
        btnOk.setOnMouseClicked((evt) -> {
            callback.run();
            hideField();
        });
    }

    @FXML
    private void initialize() {
        label.setOnMouseClicked((evt) -> {
            label.setVisible(false);
            label.setManaged(false);
            controls.setManaged(true);
            controls.setVisible(true);
            textField.setText(label.getText());
        });

        btnCancel.setOnMouseClicked((evt) -> hideField());
    }

    private void hideField() {
        label.setVisible(true);
        label.setManaged(true);
        controls.setManaged(false);
        controls.setVisible(false);
    }
}
