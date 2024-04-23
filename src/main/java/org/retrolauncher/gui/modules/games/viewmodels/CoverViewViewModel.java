package org.retrolauncher.gui.modules.games.viewmodels;

import org.retrolauncher.gui.modules.games.gateways.GamesGateway;

import java.io.File;

public class CoverViewViewModel {
    private final GamesGateway gateway;

    public CoverViewViewModel(GamesGateway gateway) {
        this.gateway = gateway;
    }

    public void saveCover(String id, File icon) {
        this.gateway.saveCover(id, icon);
    }

    public void createShortcut(String id) {
        this.gateway.createShortcut(id);
    }
}
