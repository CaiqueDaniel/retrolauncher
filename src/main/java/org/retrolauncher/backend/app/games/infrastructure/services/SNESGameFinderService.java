package org.retrolauncher.backend.app.games.infrastructure.services;

public class SNESGameFinderService extends ExtensionGameFinderService {
    public SNESGameFinderService() {
        super(new String[]{"snes"});
    }
}
