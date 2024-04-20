package org.retrolauncher.backend.app.games.infrastructure.database.jackson.repositories;

import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.mappers.JacksonGameMapper;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.database.FileDatabaseDriver;

import java.util.*;

public class JacksonGameRepository implements GameRepository {
    private final FileDatabaseDriver<Map<String, GameModel>> driver;
    private static final String FILE_PATH = new StringBuilder(System.getProperty("user.home"))
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
            Map<String, GameModel> storedData = this.driver.read(JacksonGameRepository.FILE_PATH);
            storedData.put(entity.getId().toString(), JacksonGameMapper.fromDomain(entity));
            this.driver.write(storedData, JacksonGameRepository.FILE_PATH);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void clear() {
        try {
            this.driver.clear(JacksonGameRepository.FILE_PATH);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Game> findById(UUID id) {
        try {
            Map<String, GameModel> storedData = this.driver.read(JacksonGameRepository.FILE_PATH);
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
            return this.driver.read(JacksonGameRepository.FILE_PATH)
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
            return this.driver.read(JacksonGameRepository.FILE_PATH)
                    .values()
                    .stream()
                    .anyMatch((game) -> game.getPath().equals(path));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Game> findOneByNameAndPlatformId(String name, UUID platformId) {
        try {
            return this.driver.read(JacksonGameRepository.FILE_PATH)
                    .values()
                    .stream()
                    .filter((game) -> game.getName().equals(name) && game.getPlatformId().equals(platformId.toString()))
                    .findFirst()
                    .map((model) -> {
                        Optional<Platform> platform = this.platformRepository.findById(
                                UUID.fromString(model.getPlatformId())
                        );
                        return platform.map(value -> JacksonGameMapper.toDomain(model, value)).orElse(null);
                    });
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Game> findAllByIdsNotIn(Set<String> exceptionsIds) {
        try {
            return this.driver.read(JacksonGameRepository.FILE_PATH)
                    .values()
                    .stream()
                    .filter((game) -> !exceptionsIds.contains(game.getId().toString()))
                    .map((model) -> {
                        Optional<Platform> platform = this.platformRepository.findById(
                                UUID.fromString(model.getPlatformId())
                        );
                        return platform.map(value -> JacksonGameMapper.toDomain(model, value)).orElse(null);
                    }).toList();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void delete(Game game) {
        try {
            Map<String, GameModel> storedData = this.driver.read(JacksonGameRepository.FILE_PATH);
            storedData.remove(game.getId().toString());
            this.driver.write(storedData, JacksonGameRepository.FILE_PATH);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
