package org.retrolauncher.gui.shared.viewmodels;

import org.retrolauncher.gui.modules.games.gateways.GamesGateway;
import org.retrolauncher.gui.shared.gateways.PlatformsGateway;

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
