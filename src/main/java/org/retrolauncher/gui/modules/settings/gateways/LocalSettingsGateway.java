package org.retrolauncher.gui.modules.settings.gateways;

import org.retrolauncher.backend.app._shared.application.exceptions.RetroarchNotFoundException;
import org.retrolauncher.backend.app._shared.domain.exceptions.EntityValidationException;
import org.retrolauncher.backend.app.settings.infrastructure.desktop.dtos.*;
import org.retrolauncher.backend.facades.*;
import org.retrolauncher.gui.base.exceptions.FormRequestException;
import org.retrolauncher.gui.modules.settings.models.Settings;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.*;

public class LocalSettingsGateway implements SettingsGateway {
    private final SettingFacade facade = new SettingFacadeImpl();

    @Override
    public Optional<Settings> get() {
        try {
            final SettingsOutputDto response = facade.get();
            return Optional.of(new Settings(
                    Path.of(response.romsFolderPath()),
                    Path.of(response.retroarchFolderPath())
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public CompletableFuture<Void> save(Settings settings) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final CompletableFuture<Void> result = CompletableFuture.supplyAsync(() -> {
            try {
                facade.save(new SaveSettingsDto(
                        settings.getRomPath().toString(),
                        settings.getRetroarchPath().toString()
                ));
            } catch (RetroarchNotFoundException exception) {
                throw new FormRequestException(exception.getMessage());
            } catch (EntityValidationException exception) {
                throw new FormRequestException(exception.getMessage(), exception.getErrors());
            }
            return null;
        }, executorService);
        executorService.shutdown();
        return result;
    }
}
