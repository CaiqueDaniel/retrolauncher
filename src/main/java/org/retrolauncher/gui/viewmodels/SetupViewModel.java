package org.retrolauncher.gui.viewmodels;

import org.retrolauncher.gui.gateways.SettingGateway;
import org.retrolauncher.gui.models.Setup;

import java.util.Optional;

public class SetupViewModel {
    private final SettingGateway settingGateway;

    public SetupViewModel() {
        this.settingGateway = new SettingGateway();
    }

    public void save(Setup setup) {
        this.settingGateway.save(setup);
    }

    public Optional<Setup> get() {
        return this.settingGateway.get();
    }
}
