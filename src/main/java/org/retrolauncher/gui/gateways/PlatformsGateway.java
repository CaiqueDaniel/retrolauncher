package org.retrolauncher.gui.gateways;

import org.retrolauncher.backend.facades.PlatformsFacade;
import org.retrolauncher.backend.facades.PlatformsFacadeImpl;

public class PlatformsGateway {
    private final PlatformsFacade platformsFacade;

    public PlatformsGateway() {
        this.platformsFacade = new PlatformsFacadeImpl();
    }

    public void updateList() {
        this.platformsFacade.updateList();
    }
}
