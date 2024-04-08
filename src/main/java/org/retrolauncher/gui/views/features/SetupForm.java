package org.retrolauncher.gui.views.features;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.retrolauncher.Main;
import org.retrolauncher.gui.components.DirectoryField;
import org.retrolauncher.gui.models.Setup;
import org.retrolauncher.gui.viewmodels.SetupViewModel;

import java.util.Optional;

public class SetupForm extends VBox {
    @FXML
    private DirectoryField txtRomsPath;

    @FXML
    private DirectoryField txtRetroarchPath;

    @FXML
    private Button btnSubmit;

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

        this.addEventListeners();
    }

    private void addEventListeners() {
        this.btnSubmit.setOnMouseClicked((evt) -> this.onSubmit());
    }

    private void onSubmit() {
        Optional<String> romsPath = this.txtRomsPath.getValue();
        Optional<String> retroarchPath = this.txtRetroarchPath.getValue();

        if (romsPath.isEmpty()) this.txtRomsPath.setError("Campo obrigatório");
        if (retroarchPath.isEmpty()) this.txtRetroarchPath.setError("Campo obrigatório");

        if (romsPath.isPresent() && retroarchPath.isPresent())
            this.viewModel.save(new Setup(romsPath.get(), retroarchPath.get()));
    }
}