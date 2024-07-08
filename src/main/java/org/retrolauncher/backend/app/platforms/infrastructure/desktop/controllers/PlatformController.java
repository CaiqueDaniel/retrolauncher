package org.retrolauncher.backend.app.platforms.infrastructure.desktop.controllers;

import org.retrolauncher.backend.app.platforms.application.exceptions.CoreFolderNotFoundException;
import org.retrolauncher.backend.app.platforms.application.usecases.UpdatePlatformsListUseCase;

public class PlatformController {
    private final UpdatePlatformsListUseCase updatePlatformsListUseCase;

    public PlatformController(UpdatePlatformsListUseCase updatePlatformsListUseCase) {
        this.updatePlatformsListUseCase = updatePlatformsListUseCase;
    }

    public void updateList() throws CoreFolderNotFoundException {
        this.updatePlatformsListUseCase.execute();
    }
}
