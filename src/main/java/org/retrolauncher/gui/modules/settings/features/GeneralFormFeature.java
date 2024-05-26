package org.retrolauncher.gui.modules.settings.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.modules.settings.gateways.LocalSettingsGateway;
import org.retrolauncher.gui.modules.settings.presenters.GeneralFormPresenter;
import org.retrolauncher.gui.modules.settings.presenters.IGeneralFormPresenter;
import org.retrolauncher.gui.shared.components.DirectoryInput;

import java.io.IOException;
import java.util.Optional;

public class GeneralFormFeature extends VBox implements IGeneralFormFeature {
    @FXML
    private DirectoryInput txtRomsPath, txtRetroarchPath;
    @FXML
    private Button btnSubmit;
    private IGeneralFormPresenter presenter;

    public GeneralFormFeature() {
        presenter = new GeneralFormPresenter(this, new LocalSettingsGateway());
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("modules/settings/features/GeneralFormFeature.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        btnSubmit.setOnMouseClicked((evt) -> presenter.submit());
    }

    @Override
    public Optional<String> getRetroarchPath() {
        return txtRetroarchPath.getValue();
    }

    @Override
    public Optional<String> getRomsPath() {
        return txtRomsPath.getValue();
    }
}
