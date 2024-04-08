package org.retrolauncher.backend.app.settings.domain.repositories;

import org.retrolauncher.backend.app.settings.domain.entities.Setting;

import java.util.Optional;

public interface SettingRepository {
    void save(Setting entity);

    Optional<Setting> get();
}
