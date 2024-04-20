package org.retrolauncher.backend.app.games.infrastructure.database.jackson.repositories;

import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.mappers.JacksonGameMapper;
import org.retrolauncher.backend.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;

import java.nio.file.Path;
import java.util.*;

public class MemoryGameRepository implements GameRepository {
    private final Map<String, GameModel> storedData = new HashMap<>();
    private final PlatformRepository platformRepository;

    public MemoryGameRepository(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Override
    public void save(Game entity) {
        this.storedData.put(entity.getId().toString(), JacksonGameMapper.fromDomain(entity));
    }

    @Override
    public void clear() {
        this.storedData.clear();
    }

    @Override
    public Optional<Game> findById(UUID id) {
        GameModel model = this.storedData.get(id.toString());
        if (model == null)
            return Optional.empty();
        return this.platformRepository.findById(UUID.fromString(model.getPlatformId()))
                .map(value -> JacksonGameMapper.toDomain(model, value));
    }

    @Override
    public List<Game> listAll() {
        return this.storedData.values()
                .stream()
                .map((model) -> this.platformRepository.findById(UUID.fromString(model.getPlatformId()))
                        .map(value -> JacksonGameMapper.toDomain(model, value))
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public boolean existsByPath(String path) {
        return this.storedData.values().stream()
                .anyMatch((game) -> Path.of(game.getPath()).toAbsolutePath().toString().equals(path));
    }

    @Override
    public Optional<Game> findOneByNameAndPlatformId(String name, UUID platformId) {
        return this.storedData.values()
                .stream()
                .filter((game) -> game.getName().equals(name) && game.getPlatformId().equals(platformId.toString()))
                .findFirst()
                .map((model) -> {
                    Optional<Platform> platform = this.platformRepository.findById(
                            UUID.fromString(model.getPlatformId())
                    );
                    return platform.map(value -> JacksonGameMapper.toDomain(model, value)).orElse(null);
                });
    }

    @Override
    public List<Game> findAllByIdsNotIn(Set<String> exceptionsIds) {
        try {
            return this.storedData.values()
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
            this.storedData.remove(game.getId().toString());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
