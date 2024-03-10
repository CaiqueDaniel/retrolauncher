package org.retrolauncher.backend.app.games.application.dtos;

import java.io.File;

public record SaveGameCoverInputDto(
        String id,
        File icon
) {
}
