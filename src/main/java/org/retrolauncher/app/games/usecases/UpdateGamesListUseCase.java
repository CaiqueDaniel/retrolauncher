package org.retrolauncher.app.games.usecases;

import org.retrolauncher.app.games.domain.entities.Game;
import org.retrolauncher.app.games.domain.repositories.GameRepository;
import org.retrolauncher.app.platforms.domain.entities.Platform;
import org.retrolauncher.app.platforms.domain.repositories.PlatformRepository;

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
        List<File> gamesFiles = this.getGames(folderPath);

        List<Game> games = gamesFiles.stream().map((gameFile) -> {
            Optional<Platform> gamePlatform = platforms.stream()
                    .filter((platform) -> platform
                            .getExtensions()
                            .stream()
                            .anyMatch((extension) -> gameFile.getName().contains(extension))).findFirst();

            if (gamePlatform.isEmpty())
                return null;

            return new Game(gameFile.getName(), gameFile.getPath(), "", gamePlatform.get());
        }).filter(Objects::nonNull).toList();

        games.forEach(this.repository::save);
    }

    private List<File> getGames(String folderPath) throws FileNotFoundException {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory())
            throw new FileNotFoundException();

        File[] items = folder.listFiles();

        if (items == null)
            return new ArrayList<>();

        List<File> games = new ArrayList<>();

        for (File item : items) {
            if (item.isDirectory()) {
                games.addAll(this.getGames(item.getAbsolutePath()));
                continue;
            }

            games.add(item);
        }

        return games;
    }
}
