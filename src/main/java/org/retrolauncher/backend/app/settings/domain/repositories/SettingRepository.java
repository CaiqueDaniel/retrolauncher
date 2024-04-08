package org.retrolauncher.backend.app.settings.domain.repositories;

import org.retrolauncher.backend.app.settings.domain.entities.Setting;

public interface SettingRepository {
    void save(Setting entity);
}
