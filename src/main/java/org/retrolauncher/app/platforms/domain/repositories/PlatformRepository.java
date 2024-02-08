package org.retrolauncher.app.platforms.domain.repositories;

import org.retrolauncher.app._shared.domain.repository.Repository;
import org.retrolauncher.app.platforms.domain.entities.Platform;

public interface PlatformRepository extends Repository<Platform> {
    boolean existsByCore(String corePath);
}
