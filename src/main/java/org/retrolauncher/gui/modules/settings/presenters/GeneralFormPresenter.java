package org.retrolauncher.gui.modules.settings.presenters;

import org.retrolauncher.gui.modules.settings.features.IGeneralFormFeature;
import org.retrolauncher.gui.modules.settings.gateways.SettingsGateway;
import org.retrolauncher.gui.modules.settings.models.Settings;
import org.retrolauncher.gui.router.Router;
import org.retrolauncher.gui.router.Routes;

import java.nio.file.Path;
import java.util.Optional;

public class GeneralFormPresenter implements IGeneralFormPresenter {
    private final IGeneralFormFeature view;
    private final SettingsGateway gateway;
    private final Router router;

    public GeneralFormPresenter(IGeneralFormFeature view, SettingsGateway gateway, Router router) {
        this.view = view;
        this.gateway = gateway;
        this.router = router;
    }

    @Override
    public void submit() {
        Optional<String> romsPath = view.getRomsPath();
        Optional<String> retroarchPath = view.getRetroarchPath();

        if (romsPath.isPresent() && retroarchPath.isPresent()) {
            gateway.save(new Settings(Path.of(romsPath.get()), Path.of(retroarchPath.get())));
            router.navigateTo(Routes.GAMES);
        }
    }
}
