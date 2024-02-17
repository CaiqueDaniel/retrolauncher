package org.retrolauncher.app.games.infrastructure.database.jackson.repositories;

import org.retrolauncher.app.games.domain.entities.Game;
import org.retrolauncher.app.games.domain.repositories.GameRepository;
import org.retrolauncher.app.games.infrastructure.database.jackson.mappers.JacksonGameMapper;
import org.retrolauncher.app.games.infrastructure.database.jackson.models.GameModel;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;

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
    public boolean existsByGamePath(String gamePath) {
        return this.storedData.values().stream().anyMatch((game) -> game.getPath().equals(gamePath));
    }
}
