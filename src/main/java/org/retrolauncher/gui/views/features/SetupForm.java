package org.retrolauncher.gui.views.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.Routes;
import org.retrolauncher.gui.components.DirectoryField;
import org.retrolauncher.gui.models.Setting;
import org.retrolauncher.gui.viewmodels.SetupViewModel;

import java.util.Optional;

public class SetupForm extends VBox {
    @FXML
    private DirectoryField txtRomsPath;

    @FXML
    private DirectoryField txtRetroarchPath;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnGoBack;

    private final SetupViewModel viewModel;

    public SetupForm() {
        this.viewModel = new SetupViewModel();
        this.load();
    }

    private void load() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/features/SetupForm.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        this.txtRomsPath.setLabel("Selecione a localização das ROMs:");
        this.txtRetroarchPath.setLabel("Selecione a localização do RetroArch:");

        this.fillForm();
        this.addEventListeners();
    }

    private void fillForm() {
        Optional<Setting> settings = this.viewModel.get();

        settings.ifPresent((data) -> {
            this.txtRomsPath.setValue(data.romPath());
            this.txtRetroarchPath.setValue(data.retroarchPath());
        });
    }

    private void addEventListeners() {
        btnSubmit.setOnMouseClicked((evt) -> this.onSubmit());
        btnGoBack.setOnMouseClicked((evt) -> Routes.getInstance().switchToHome());
    }

    private void onSubmit() {
        Optional<String> romsPath = this.txtRomsPath.getValue();
        Optional<String> retroarchPath = this.txtRetroarchPath.getValue();

        if (romsPath.isEmpty()) this.txtRomsPath.setError("Campo obrigatório");
        if (retroarchPath.isEmpty()) this.txtRetroarchPath.setError("Campo obrigatório");

        if (romsPath.isPresent() && retroarchPath.isPresent()) {
            this.viewModel.save(new Setting(romsPath.get(), retroarchPath.get()));
            Routes.getInstance().switchToHome();
        }
    }
}
