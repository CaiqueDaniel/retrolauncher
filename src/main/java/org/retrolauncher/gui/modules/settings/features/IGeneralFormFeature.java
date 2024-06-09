package org.retrolauncher.gui.modules.settings.features;

import java.util.Optional;

public interface IGeneralFormFeature {
    Optional<String> getRetroarchPath();

    Optional<String> getRomsPath();

    void setRetroarchPath(String value);

    void setRomsPath(String value);

    void setErrorMessage(String message);

    void setFieldsValidationError(String fieldName, String message);
}

