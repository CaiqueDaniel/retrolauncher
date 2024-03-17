package org.retrolauncher.backend.app.games.application.usecases;

import org.retrolauncher.backend.app.games.domain.entities.Game;
import org.retrolauncher.backend.app.games.domain.repositories.GameRepository;
import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.domain.repositories.PlatformRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UpdateGamesListUseCase {
    private final GameRepository repository;
    private final PlatformRepository platformRepository;

    public UpdateGamesListUseCase(GameRepository repository, PlatformRepository platformRepository) {
        this.repository = repository;
        this.platformRepository = platformRepository;
    }

    public void execute(String folderPath) throws FileNotFoundException {
        List<Platform> platforms = this.platformRepository.listAll();
        List<File> gamesFiles = this.getGamesFiles(folderPath);

        List<Game> games = gamesFiles.stream().map((gameFile) -> {
            Optional<Platform> gamePlatform = platforms.stream()
                    .filter((platform) -> platform
                            .getExtensions()
                            .stream()
                            .anyMatch((extension) -> gameFile.getName().contains(extension))).findFirst();

            if (gamePlatform.isEmpty())
                return null;

            return new Game(gameFile.getName().substring(0, gameFile.getName().lastIndexOf('.')), gameFile.getAbsolutePath(), gamePlatform.get());
        }).filter(Objects::nonNull).toList();

        games.forEach(this.repository::save);
    }

    private List<File> getGamesFiles(String folderPath) throws FileNotFoundException {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory())
            throw new FileNotFoundException();

        File[] items = folder.listFiles();

        if (items == null)
            return new ArrayList<>();

        List<File> games = new ArrayList<>();

        for (File item : items) {
            if (item.isDirectory()) {
                games.addAll(this.getGamesFiles(item.getAbsolutePath()));
                continue;
            }

            if (this.repository.existsByPath(item.getAbsolutePath()))
                continue;

            games.add(item);
        }

        return games;
    }
}
