package org.retrolauncher.backend.app.platforms.domain.repositories;

import org.retrolauncher.backend.app._shared.domain.repository.Repository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;

public interface PlatformRepository extends Repository<Platform> {
    boolean existsByCore(String corePath);
}
