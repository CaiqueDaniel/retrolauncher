package org.retrolauncher.backend.app._shared.infrastructure.services;

import mslinks.ShellLink;
import org.retrolauncher.backend.app._shared.application.dtos.Shortcut;
import org.retrolauncher.backend.app._shared.application.services.ShortcutService;

import java.io.IOException;

public class ShellLinkShortcutService implements ShortcutService {
    @Override
    public void create(Shortcut shortcut) throws IOException {
        String shortcutRelativePath = new StringBuilder(System.getProperty("user.home"))
                .append("/Desktop/")
                .append(shortcut.title())
                .append(".lnk")
                .toString();

        new ShellLink()
                .setTarget(shortcut.targetPath())
                .setCMDArgs(shortcut.args())
                .setIconLocation(shortcut.iconPath().toString())
                .saveTo(shortcutRelativePath);
    }
}
