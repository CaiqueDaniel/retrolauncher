package org.retrolauncher.backend.facades;

import org.retrolauncher.backend.Backend;

public class PlatformsFacadeImpl implements PlatformsFacade {
    @Override
    public void updateList() {
        Backend.getDependencies().getPlatformController().updateList();
    }
}
