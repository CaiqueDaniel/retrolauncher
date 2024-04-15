package org.retrolauncher.backend.app.games.application.dtos;

import java.nio.file.Path;
import java.util.Optional;

public record GameInfoOutputDto(
        String id,
        String name,
        Optional<Path> iconPath
) {
}
