package org.retrolauncher.backend.app._shared.application.services;

import org.retrolauncher.backend.app._shared.application.dtos.Shortcut;

import java.io.IOException;

public interface ShortcutService {
    void create(Shortcut shortcut) throws IOException;
}
