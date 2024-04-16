package org.retrolauncher.backend.app._shared.application.dtos;

import java.nio.file.Path;

public record Shortcut(
        String title,
        Path targetPath,
        String args,
        Path iconPath
) {
}
