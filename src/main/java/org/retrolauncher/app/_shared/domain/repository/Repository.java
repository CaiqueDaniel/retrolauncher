package org.retrolauncher.app._shared.domain.repository;

import org.retrolauncher.app._shared.domain.entities.Entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<E extends Entity> {
    void save(E entity);

    void clear();

    Optional<E> findById(UUID id);

    List<E> listAll();
}
