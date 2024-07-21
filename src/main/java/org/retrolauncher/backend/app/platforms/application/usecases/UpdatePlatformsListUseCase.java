package org.retrolauncher.backend.app.platforms.application.usecases;

import org.retrolauncher.backend.app.platforms.application.dtos.PlatformConfigDto;
import org.retrolauncher.backend.app.platforms.application.exceptions.CoreFolderNotFoundException;
import org.retrolauncher.backend.app.platforms.application.services.PlatformsResourceConfigService;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.settings.application.exceptions.SettingNotFoundException;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class UpdatePlatformsListUseCase {
    private final PlatformRepository repository;
    private final SettingRepository settingRepository;
    private final PlatformsResourceConfigService platformsResourceConfigService;

    public UpdatePlatformsListUseCase(
            PlatformRepository repository,
            SettingRepository settingRepository,
            PlatformsResourceConfigService platformsResourceConfigService
    ) {
        this.repository = repository;
        this.settingRepository = settingRepository;
        this.platformsResourceConfigService = platformsResourceConfigService;
    }

    public void execute() throws CoreFolderNotFoundException {
        Optional<Setting> setting = this.settingRepository.get();

        if (setting.isEmpty())
            throw new SettingNotFoundException();

        List<File> cores = this.getCores(setting.get().getRetroarchFolderPath());
        List<Platform> platforms = this.getPlatforms(cores);

        platforms.forEach(this.repository::save);
    }

    private List<File> getCores(Path coreFolderPath) throws CoreFolderNotFoundException {
        File file = coreFolderPath.endsWith("cores") ?
                coreFolderPath.toFile() :
                Path.of(coreFolderPath + "/cores").toFile();

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
            return new Platform(config.name(), platformCore.get().getAbsolutePath());
        }).filter(Objects::nonNull).toList();
    }
}
