package org.retrolauncher.gui.modules.settings.features;

import java.util.Optional;

public interface IGeneralFormFeature {
    Optional<String> getRetroarchPath();

    Optional<String> getRomsPath();
}
