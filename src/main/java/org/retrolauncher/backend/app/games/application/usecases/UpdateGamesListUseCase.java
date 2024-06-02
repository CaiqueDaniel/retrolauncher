package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.Main;
import org.retrolauncher.backend.app._shared.application.exceptions.FileCouldNotBeDeletedException;
import org.retrolauncher.backend.app._shared.application.services.FileManagerService;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UpdateGamesListUseCase {
    private final GameRepository repository;
    private final PlatformRepository platformRepository;
    private final SettingRepository settingRepository;
    private final FileManagerService fileManagerService;

    public UpdateGamesListUseCase(
            GameRepository repository,
            PlatformRepository platformRepository,
            SettingRepository settingRepository,
            FileManagerService fileManagerService
    ) {
        this.repository = repository;
        this.platformRepository = platformRepository;
        this.settingRepository = settingRepository;
        this.fileManagerService = fileManagerService;
    }

    public void execute() throws FileNotFoundException {
        Optional<Setting> setting = this.settingRepository.get();

        if (setting.isEmpty())
            throw new SettingNotFoundException();

        List<Game> indexedGames = getIndexedGames(setting.get());
        List<Game> gamesToBeSaved = getGamesToBeSaved(indexedGames);
        List<Game> gamesToBeRemoved = getGamesToBeRemoved(gamesToBeSaved);

        gamesToBeSaved.forEach(this.repository::save);

        removeGames(gamesToBeRemoved);
    }

    private List<Game> getIndexedGames(Setting setting) throws FileNotFoundException {
        List<Platform> platforms = this.platformRepository.listAll();
        List<File> gamesFiles = this.getGamesFiles(setting.getRomsFolderPath());

        return gamesFiles.stream().map((gameFile) -> {
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
    }

    private List<Game> getGamesToBeSaved(List<Game> games) {
        return games.stream().map((game) -> {
            Optional<Game> result = this.repository.findOneByNameAndPlatformId(
                    game.getName(),
                    game.getPlatform().getId()
            );

            if (result.isEmpty())
                return game;

            Game foundGame = result.get();
            foundGame.updatePath(game.getPath());
            return foundGame;
        }).toList();
    }

    private List<Game> getGamesToBeRemoved(List<Game> games) {
        return this.repository.findAllByIdsNotIn(
                games.stream()
                        .map((game) -> game.getId().toString())
                        .collect(Collectors.toSet())
        );
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
            games.add(item);
        }

        return games;
    }

    private String getNameWithoutExtension(File file) {
        return file.getName().substring(0, file.getName().lastIndexOf('.'));
    }

    private void removeGames(List<Game> games) {
        games.forEach((game) -> {
            try {
                if (game.getIconPath().isPresent())
                    fileManagerService.delete(game.getIconPath().get());
                repository.delete(game);
            } catch (FileCouldNotBeDeletedException e) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, e.getMessage());
            }
        });
    }
}
