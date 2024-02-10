package org.retrolauncher.app.platforms.infrastructure.database.jackson.repositories;

import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.mappers.JacksonPlatformMapper;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.models.PlatformModel;

import java.util.*;

public class MemoryPlatformRepository implements PlatformRepository {
    private final Map<String, PlatformModel> storedData = new HashMap<>();

    @Override
    public void save(Platform entity) {
        this.storedData.put(entity.getId().toString(), JacksonPlatformMapper.fromDomain(entity));
    }

    @Override
    public void clear() {
        this.storedData.clear();
    }

    @Override
    public Optional<Platform> findById(UUID id) {
        PlatformModel model = this.storedData.get(id.toString());
        if (model == null)
            return Optional.empty();
        return Optional.of(JacksonPlatformMapper.toDomain(model));
    }

    @Override
    public List<Platform> listAll() {
        return this.storedData
                .values()
                .stream()
                .map(JacksonPlatformMapper::toDomain).toList();
    }

    @Override
    public boolean existsByCore(String corePath) {
        return this.storedData
                .values()
                .stream()
                .anyMatch((platformModel -> platformModel.getCorePath().equals(corePath)));
    }
}
