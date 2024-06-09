package org.retrolauncher.gui.modules.settings.presenters;

import javafx.application.Platform;
import org.retrolauncher.gui.base.exceptions.FormRequestException;
import org.retrolauncher.gui.modules.settings.features.IGeneralFormFeature;
import org.retrolauncher.gui.modules.settings.gateways.SettingsGateway;
import org.retrolauncher.gui.modules.settings.models.Settings;
import org.retrolauncher.gui.router.Router;
import org.retrolauncher.gui.router.Routes;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class GeneralFormPresenter implements IGeneralFormPresenter {
    private final IGeneralFormFeature view;
    private final SettingsGateway gateway;
    private final Router router;

    public GeneralFormPresenter(IGeneralFormFeature view, SettingsGateway gateway, Router router) {
        this.view = view;
        this.gateway = gateway;
        this.router = router;
    }

    @Override
    public void loadInitialValues() {
        final Optional<Settings> response = gateway.get();
        response.ifPresent((settings) -> {
            view.setRetroarchPath(settings.getRetroarchPath().toString());
            view.setRomsPath(settings.getRomPath().toString());
        });
    }

    @Override
    public void submit() {
        final Optional<String> romsPath = view.getRomsPath();
        final Optional<String> retroarchPath = view.getRetroarchPath();

        if (romsPath.isPresent() && retroarchPath.isPresent()) {
            final CompletableFuture<Void> promise = gateway.save(new Settings(
                    Path.of(romsPath.get()),
                    Path.of(retroarchPath.get())
            ));
            promise.thenAccept((r) -> Platform.runLater(() -> router.navigateTo(Routes.GAMES)))
                    .exceptionally((exception) -> {
                        Platform.runLater(() -> {
                            final FormRequestException cause = (FormRequestException) exception.getCause();
                            view.setErrorMessage(cause.getMessage());
                            cause.getErrors().forEach(view::setFieldsValidationError);
                        });
                        return null;
                    });
        }
    }
}
