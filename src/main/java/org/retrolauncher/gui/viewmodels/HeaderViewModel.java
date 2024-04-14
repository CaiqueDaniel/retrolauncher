package org.retrolauncher.gui.viewmodels;

import org.retrolauncher.gui.gateways.GamesGateway;
import org.retrolauncher.gui.gateways.PlatformsGateway;

public class HeaderViewModel {
    private final GamesGateway gamesGateway;
    private final PlatformsGateway platformsGateway;

    public HeaderViewModel() {
        this.gamesGateway = new GamesGateway();
        this.platformsGateway = new PlatformsGateway();
    }

    public void updateList() {
        this.platformsGateway.updateList();
        this.gamesGateway.updateList();
    }
}
