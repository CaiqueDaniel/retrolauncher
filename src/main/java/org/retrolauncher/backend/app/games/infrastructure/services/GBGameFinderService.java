package org.retrolauncher.backend.app.games.infrastructure.services;

public class GBGameFinderService extends ExtensionGameFinderService {
    public GBGameFinderService() {
        super(new String[]{"gb", "gbc"});
    }
}
