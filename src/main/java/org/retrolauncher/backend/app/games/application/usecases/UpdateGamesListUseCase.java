package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.settings.application.exceptions.SettingNotFoundException;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UpdateGamesListUseCase {
    private final GameRepository repository;
    private final PlatformRepository platformRepository;
    private final SettingRepository settingRepository;

    public UpdateGamesListUseCase(
            GameRepository repository,
            PlatformRepository platformRepository,
            SettingRepository settingRepository
    ) {
        this.repository = repository;
        this.platformRepository = platformRepository;
        this.settingRepository = settingRepository;
    }

    public void execute() throws FileNotFoundException {
        Optional<Setting> setting = this.settingRepository.get();

        if (setting.isEmpty())
            throw new SettingNotFoundException();

        List<Platform> platforms = this.platformRepository.listAll();
        List<File> gamesFiles = this.getGamesFiles(setting.get().getRomsFolderPath());

        List<Game> games = gamesFiles.stream().map((gameFile) -> {
            Optional<Platform> gamePlatform = platforms.stream()
                    .filter((platform) -> platform
                            .getExtensions()
                            .stream()
                            .anyMatch((extension) -> gameFile.getName().contains(extension))).findFirst();

            return gamePlatform.map(platform -> new Game(
                    getNameWithoutExtension(gameFile),
                    gameFile.toPath(),
                    platform
            )).orElse(null);
        }).filter(Objects::nonNull).toList();

        games.forEach(this.repository::save);
    }

    private List<File> getGamesFiles(Path folderPath) throws FileNotFoundException {
        File folder = folderPath.toFile();

        if (!folder.exists() || !folder.isDirectory())
            throw new FileNotFoundException();

        File[] items = folder.listFiles();

        if (items == null)
            return new ArrayList<>();

        List<File> games = new ArrayList<>();

        for (File item : items) {
            if (item.isDirectory()) continue;
            if (this.repository.existsByPath(item.getAbsolutePath())) continue;

            games.add(item);
        }

        return games;
    }

    private String getNameWithoutExtension(File file) {
        return file.getName().substring(0, file.getName().lastIndexOf('.'));
    }
}
