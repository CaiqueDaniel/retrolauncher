package org.retrolauncher.backend.app.platforms.application.services;

import org.retrolauncher.backend.app.platforms.application.dtos.PlatformConfigDto;

import java.util.List;

public interface PlatformsResourceConfigService {
    List<PlatformConfigDto> load() throws RuntimeException;
}

