package org.retrolauncher.app.games.infrastructure.database.jackson.repositories;

import org.retrolauncher.app.games.domain.entities.Game;
import org.retrolauncher.app.games.domain.repositories.GameRepository;
import org.retrolauncher.app.games.infrastructure.database.jackson.mappers.JacksonGameMapper;
import org.retrolauncher.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.database.FileDatabaseDriver;

import java.util.*;

public class JacksonGameRepository implements GameRepository {
    private final FileDatabaseDriver<Map<String, GameModel>> driver;
    private static final String filePath = new StringBuilder(System.getProperty("user.home"))
            .append("/retro-launcher")
            .append("/data")
            .append("/games.json")
            .toString();
    private final PlatformRepository platformRepository;

    public JacksonGameRepository(
            FileDatabaseDriver<Map<String, GameModel>> driver,
            PlatformRepository platformRepository
    ) {
        this.driver = driver;
        this.platformRepository = platformRepository;
    }

    @Override
    public void save(Game entity) {
        try {
            Map<String, GameModel> storedData = this.driver.read(JacksonGameRepository.filePath);
            storedData.put(entity.getId().toString(), JacksonGameMapper.fromDomain(entity));
            this.driver.write(storedData, JacksonGameRepository.filePath);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void clear() {
        try {
            this.driver.clear(JacksonGameRepository.filePath);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Game> findById(UUID id) {
        try {
            Map<String, GameModel> storedData = this.driver.read(JacksonGameRepository.filePath);
            GameModel model = storedData.get(id.toString());

            if (model == null)
                return Optional.empty();

            Optional<Platform> platform = this.platformRepository.findById(UUID.fromString(model.getPlatformId()));

            return platform.map(value -> JacksonGameMapper.toDomain(model, value));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Game> listAll() {
        try {
            return this.driver.read(JacksonGameRepository.filePath)
                    .values()
                    .stream()
                    .map((model) -> this.platformRepository.findById(UUID.fromString(model.getPlatformId()))
                            .map(value -> JacksonGameMapper.toDomain(model, value))
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public boolean existsByPath(String path) {
        try {
            return this.driver.read(JacksonGameRepository.filePath)
                    .values()
                    .stream()
                    .anyMatch((game) -> game.getPath().equals(path));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
