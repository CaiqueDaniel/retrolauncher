package fixtures;

import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;

import java.util.Optional;

public class StubSettingRepository implements SettingRepository {
    private Setting setting;

    @Override
    public void save(Setting entity) {
        this.setting = entity;
    }

    public Optional<Setting> get() {
        return Optional.ofNullable(this.setting);
    }

    public void clear() {
        this.setting = null;
    }
}
