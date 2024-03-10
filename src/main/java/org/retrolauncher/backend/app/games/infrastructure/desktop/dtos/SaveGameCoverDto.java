package org.retrolauncher.backend.app.games.infrastructure.desktop.dtos;

import java.io.File;

public record SaveGameCoverDto(
        String id,
        File icon
) {
}
