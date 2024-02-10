package org.retrolauncher.app.platforms.application.dtos;

import java.util.List;

public record PlatformConfigDto(
        String name,
        List<String> extensions,
        List<String> coresAlias
) {
}