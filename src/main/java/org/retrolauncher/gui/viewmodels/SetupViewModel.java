package org.retrolauncher.gui.viewmodels;

import org.retrolauncher.gui.gateways.SettingGateway;
import org.retrolauncher.gui.models.Setting;

import java.util.Optional;

public class SetupViewModel {
    private final SettingGateway settingGateway;

    public SetupViewModel() {
        this.settingGateway = new SettingGateway();
    }

    public void save(Setting setting) {
        this.settingGateway.save(setting);
    }

    public Optional<Setting> get() {
        return this.settingGateway.get();
    }
}
