package org.retrolauncher.backend.app.games.infrastructure.services;

public class NESGameFinderService extends ExtensionGameFinderService {
    public NESGameFinderService() {
        super(new String[]{"nes"});
    }
}
