package org.retrolauncher.backend.app.games.infrastructure.services;

public class SMSPlatformDetectorService extends ExtensionReaderPlatformDetectorService {
    public SMSPlatformDetectorService() {
        super(new String[]{"sms"});
    }
}
