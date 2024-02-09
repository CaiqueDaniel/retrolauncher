package org.retrolauncher.app.platforms.infrastructure.database.jackson.repositories;

import org.retrolauncher.database.FileDatabaseDriver;
import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.models.PlatformModel;
import org.retrolauncher.app.platforms.infrastructure.database.jackson.mappers.JacksonPlatformMapper;

import java.util.*;

public class JacksonPlatformRepository implements PlatformRepository {
    private final FileDatabaseDriver<Map<String, PlatformModel>> driver;
    private static final String filePath = new StringBuilder(System.getProperty("user.home"))
            .append("/retro-launcher")
            .append("/data")
            .append("/platforms.json")
            .toString();

    public JacksonPlatformRepository(FileDatabaseDriver<Map<String, PlatformModel>> driver) {
        this.driver = driver;
    }

    @Override
    public void save(Platform entity) {
        try {
            Map<String, PlatformModel> storedData = this.driver.read(JacksonPlatformRepository.filePath);
            storedData.put(entity.getId().toString(), JacksonPlatformMapper.fromDomain(entity));
            this.driver.write(storedData, JacksonPlatformRepository.filePath);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void clear() {
        try {
            this.driver.clear(JacksonPlatformRepository.filePath);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Platform> findById(UUID id) {
        try {
            Map<String, PlatformModel> storedData = this.driver.read(JacksonPlatformRepository.filePath);
            PlatformModel model = storedData.get(id.toString());

            if (model == null)
                return Optional.empty();
            return Optional.of(JacksonPlatformMapper.toDomain(model));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Platform> listAll() {
        try {
            return this.driver.read(JacksonPlatformRepository.filePath)
                    .values()
                    .stream()
                    .map(JacksonPlatformMapper::toDomain).toList();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean existsByCore(String corePath) {
        try {
            return this.driver.read(JacksonPlatformRepository.filePath)
                    .values()
                    .stream()
                    .anyMatch((platformModel -> platformModel.getCorePath().equals(corePath)));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
