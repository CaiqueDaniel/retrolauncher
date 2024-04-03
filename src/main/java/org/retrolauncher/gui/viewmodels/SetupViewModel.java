package org.retrolauncher.gui.viewmodels;

import org.retrolauncher.gui.gateways.SetupGateway;
import org.retrolauncher.gui.models.Setup;

public class SetupViewModel {
    private final SetupGateway setupGateway;

    public SetupViewModel() {
        this.setupGateway = new SetupGateway();
    }

    public void save(Setup setup) {
        this.setupGateway.save(setup);
    }
}
