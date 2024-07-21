package org.retrolauncher.backend.app.platforms.infrastructure.database.memory;

import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;

import java.util.*;

public class MemoryPlatformRepository implements PlatformRepository {
    private final Map<String, Platform> storedData = new HashMap<>();

    @Override
    public void save(Platform entity) {
        this.storedData.put(entity.getId().toString(), entity);
    }

    public void clear() {
        this.storedData.clear();
    }

    @Override
    public Optional<Platform> findById(UUID id) {
        Platform model = this.storedData.get(id.toString());
        if (model == null)
            return Optional.empty();
        return Optional.of(model);
    }

    @Override
    public List<Platform> listAll() {
        return this.storedData
                .values()
                .stream()
                .toList();
    }

    @Override
    public boolean existsByCore(String corePath) {
        return this.storedData
                .values()
                .stream()
                .anyMatch((platformModel -> platformModel.getCorePath().equals(corePath)));
    }
}
