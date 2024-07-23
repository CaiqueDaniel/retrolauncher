package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.Main;
import org.retrolauncher.backend.app._shared.application.exceptions.FileCouldNotBeDeletedException;
import org.retrolauncher.backend.app._shared.application.services.FileManagerService;
import org.retrolauncher.backend.app.games.application.factories.GameFinderFactory;
import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;
import org.retrolauncher.backend.app.settings.application.exceptions.SettingNotFoundException;
import org.retrolauncher.backend.app.settings.domain.entities.Setting;
import org.retrolauncher.backend.app.settings.domain.repositories.SettingRepository;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

public class UpdateGamesListUseCase {
    private final GameRepository repository;
    private final PlatformRepository platformRepository;
    private final SettingRepository settingRepository;
    private final FileManagerService fileManagerService;
    private final GameFinderFactory gameFinderFactory;

    public UpdateGamesListUseCase(
            GameRepository repository,
            PlatformRepository platformRepository,
            SettingRepository settingRepository,
            FileManagerService fileManagerService,
            GameFinderFactory gameFinderFactory
    ) {
        this.repository = repository;
        this.platformRepository = platformRepository;
        this.settingRepository = settingRepository;
        this.fileManagerService = fileManagerService;
        this.gameFinderFactory = gameFinderFactory;
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

    private List<Game> getIndexedGames(Setting setting) {
        final var games = new ArrayList<Game>();
        final var platforms = this.platformRepository.listAll();

        platforms.forEach((platform) -> {
            final var files = gameFinderFactory
                    .createFrom(platform.getName())
                    .getFilesFrom(setting.getRomsFolderPath().toFile());

            files.forEach((file) -> games.add(new Game(getNameWithoutExtension(file), file.toPath(), platform.getId())));
        });

        return games;
    }

    private List<Game> getGamesToBeSaved(List<Game> games) {
        return games.stream().map((game) -> {
            Optional<Game> result = this.repository.findOneByNameAndPlatformId(
                    game.getName(),
                    game.getPlatformId()
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
