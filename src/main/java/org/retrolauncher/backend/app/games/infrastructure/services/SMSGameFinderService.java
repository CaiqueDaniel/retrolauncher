package org.retrolauncher.backend.app.games.infrastructure.services;

public class SMSGameFinderService extends ExtensionGameFinderService {
    public SMSGameFinderService() {
        super(new String[]{"sms"});
    }
}
