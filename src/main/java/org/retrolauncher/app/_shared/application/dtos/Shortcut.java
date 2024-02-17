package org.retrolauncher.app._shared.application.dtos;

public record Shortcut(
        String title,
        String targetPath,
        String args,
        String iconPath
) {
}
