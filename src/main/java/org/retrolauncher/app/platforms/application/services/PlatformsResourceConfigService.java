package org.retrolauncher.app.platforms.application.services;

import org.retrolauncher.app.platforms.application.dtos.PlatformConfigDto;

import java.util.List;

public interface PlatformsResourceConfigService {
    List<PlatformConfigDto> load() throws RuntimeException;
}

