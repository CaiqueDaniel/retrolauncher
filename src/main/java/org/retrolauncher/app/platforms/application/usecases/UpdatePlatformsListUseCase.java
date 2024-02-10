package org.retrolauncher.app.platforms.application.usecases;

import org.retrolauncher.app.platforms.application.dtos.PlatformConfigDto;
import org.retrolauncher.app.platforms.application.exceptions.CoreFolderNotFoundException;
import org.retrolauncher.app.platforms.application.services.PlatformsResourceConfigService;
import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;

import java.io.File;
import java.util.*;

public class UpdatePlatformsListUseCase {
    private final PlatformRepository repository;
    private final PlatformsResourceConfigService platformsResourceConfigService;

    public UpdatePlatformsListUseCase(
            PlatformRepository repository,
            PlatformsResourceConfigService platformsResourceConfigService
    ) {
        this.repository = repository;
        this.platformsResourceConfigService = platformsResourceConfigService;
    }

    public void execute(String coreFolderPath) throws CoreFolderNotFoundException {
        List<File> cores = this.getCores(coreFolderPath);
        List<Platform> platforms = this.getPlatforms(cores);

        platforms.forEach(this.repository::save);
    }

    private List<File> getCores(String coreFolderPath) throws CoreFolderNotFoundException {
        File file = new File(coreFolderPath);

        if (!file.exists() || !file.isDirectory())
            throw new CoreFolderNotFoundException();

        File[] cores = file.listFiles();

        if (cores == null)
            return new ArrayList<>();
        return Arrays.stream(cores).toList();
    }

    private List<Platform> getPlatforms(List<File> cores) {
        List<PlatformConfigDto> platformConfigs = this.platformsResourceConfigService.load();

        return platformConfigs.stream().map((config) -> {
            Optional<File> platformCore = config.coresAlias()
                    .stream()
                    .flatMap((alias) -> cores.stream().filter(core -> core.getName().contains(alias)))
                    .findFirst();

            if (platformCore.isEmpty() || this.repository.existsByCore(platformCore.get().getAbsolutePath()))
                return null;
            return new Platform(config.name(), platformCore.get().getAbsolutePath(), config.extensions());
        }).filter(Objects::nonNull).toList();
    }
}
