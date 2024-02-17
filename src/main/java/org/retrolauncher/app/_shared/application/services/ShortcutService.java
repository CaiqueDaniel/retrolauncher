package org.retrolauncher.app._shared.application.services;

import org.retrolauncher.app._shared.application.dtos.Shortcut;

import java.io.IOException;

public interface ShortcutService {
    void create(Shortcut shortcut) throws IOException;
}
