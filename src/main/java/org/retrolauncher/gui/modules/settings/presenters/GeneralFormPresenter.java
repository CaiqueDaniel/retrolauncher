package org.retrolauncher.gui.modules.settings.presenters;

import org.retrolauncher.gui.modules.settings.features.IGeneralFormFeature;
import org.retrolauncher.gui.modules.settings.gateways.SettingsGateway;
import org.retrolauncher.gui.modules.settings.models.Settings;

import java.nio.file.Path;
import java.util.Optional;

public class GeneralFormPresenter implements IGeneralFormPresenter {
    private final IGeneralFormFeature view;
    private final SettingsGateway gateway;

    public GeneralFormPresenter(IGeneralFormFeature view, SettingsGateway gateway) {
        this.view = view;
        this.gateway = gateway;
    }

    @Override
    public void submit() {
        Optional<String> romsPath = view.getRomsPath();
        Optional<String> retroarchPath = view.getRetroarchPath();

        if (romsPath.isPresent() && retroarchPath.isPresent()) {
            gateway.save(new Settings(Path.of(romsPath.get()), Path.of(retroarchPath.get())));
        }
    }
}
