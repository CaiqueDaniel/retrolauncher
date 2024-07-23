package org.retrolauncher.backend.app.games.infrastructure.services;

public class N64GameFinderService extends ExtensionGameFinderService {
    public N64GameFinderService() {
        super(new String[]{"n64", "z64"});
    }
}
