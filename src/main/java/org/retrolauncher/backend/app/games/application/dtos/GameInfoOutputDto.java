package org.retrolauncher.backend.app.games.application.dtos;

import java.util.Optional;

public record GameInfoOutputDto(
        String id,
        String name,
        Optional<String> iconPath
) {
}
