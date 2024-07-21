package org.retrolauncher.backend.app.games.infrastructure.database.memory;

import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;

import java.nio.file.Path;
import java.util.*;

public class MemoryGameRepository implements GameRepository {
    private final Map<String, Game> storedData = new HashMap<>();
    private final PlatformRepository platformRepository;

    public MemoryGameRepository(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Override
    public void save(Game entity) {
        this.storedData.put(entity.getId().toString(), entity);
    }

    public void clear() {
        this.storedData.clear();
    }

    @Override
    public Optional<Game> findById(UUID id) {
        Game model = this.storedData.get(id.toString());
        if (model == null)
            return Optional.empty();
        return Optional.of(model);
    }

    @Override
    public List<Game> listAll() {
        return this.storedData.values().stream().toList();
    }

    @Override
    public boolean existsByPath(String path) {
        return this.storedData.values().stream()
                .anyMatch((game) -> Path.of(game.getPath().toUri()).toAbsolutePath().toString().equals(path));
    }

    @Override
    public Optional<Game> findOneByNameAndPlatformId(String name, UUID platformId) {
        return this.storedData.values()
                .stream()
                .filter((game) -> game.getName().equals(name) && game.getPlatformId().equals(platformId))
                .findFirst();
    }

    @Override
    public List<Game> findAllByIdsNotIn(Set<String> exceptionsIds) {
        try {
            return this.storedData.values()
                    .stream()
                    .filter((game) -> !exceptionsIds.contains(game.getId().toString()))
                    .toList();
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
