package org.retrolauncher.gui.modules.settings.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.modules.settings.gateways.LocalSettingsGateway;
import org.retrolauncher.gui.modules.settings.presenters.GeneralFormPresenter;
import org.retrolauncher.gui.modules.settings.presenters.IGeneralFormPresenter;
import org.retrolauncher.gui.router.Router;
import org.retrolauncher.gui.shared.components.DirectoryInput;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class GeneralFormFeature extends VBox implements IGeneralFormFeature {
    @FXML
    private Label lblMessageError;
    @FXML
    private DirectoryInput txtRomsPath;
    @FXML
    private DirectoryInput txtRetroarchPath;
    @FXML
    private Button btnSubmit;
    private final IGeneralFormPresenter presenter;
    private final Map<String, Consumer<String>> fieldsValidationErrorHandlers = new HashMap<>();

    public GeneralFormFeature() {
        presenter = new GeneralFormPresenter(this, new LocalSettingsGateway(), Router.getInstance());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modules/settings/features/GeneralFormFeature.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<String> getRetroarchPath() {
        return txtRetroarchPath.getValue();
    }

    @Override
    public Optional<String> getRomsPath() {
        return txtRomsPath.getValue();
    }

    @Override
    public void setRetroarchPath(String value) {
        txtRetroarchPath.setValue(value);
    }

    @Override
    public void setRomsPath(String value) {
        txtRomsPath.setValue(value);
    }

    @Override
    public void setErrorMessage(String message) {
        lblMessageError.setText(message);
        lblMessageError.setVisible(true);
        lblMessageError.setManaged(true);
    }

    @Override
    public void setFieldsValidationError(String fieldName, String message) {
        fieldsValidationErrorHandlers.get(fieldName).accept(message);
    }

    @FXML
    private void initialize() {
        presenter.loadInitialValues();
        btnSubmit.setOnMouseClicked((evt) -> presenter.submit());
        registerFieldsValidationErrorHandlers();
    }

    private void registerFieldsValidationErrorHandlers() {
        fieldsValidationErrorHandlers.put("romsFolderPath", txtRomsPath::setError);
        fieldsValidationErrorHandlers.put("retroarchFolderPath", txtRetroarchPath::setError);
    }
}
