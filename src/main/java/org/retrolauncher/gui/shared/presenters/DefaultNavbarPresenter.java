package org.retrolauncher.gui.shared.presenters;

import org.retrolauncher.gui.events.EventManager;
import org.retrolauncher.gui.events.EventType;

public class DefaultNavbarPresenter implements NavbarPresenter {
    private final EventManager eventManager;

    public DefaultNavbarPresenter(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void reindexGamesList() {
        eventManager.notify(EventType.REINDEX_GAMES_REQUESTED);
    }
}
