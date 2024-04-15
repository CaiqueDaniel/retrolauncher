package org.retrolauncher.backend.app._shared.application.dtos;

import java.nio.file.Path;

public record Shortcut(
        String title,
        String targetPath,
        String args,
        Path iconPath
) {
}
