package org.retrolauncher.gui.modules.games.gateways;

import org.retrolauncher.backend.app.games.application.dtos.GameSearchParams;
import org.retrolauncher.backend.app.games.infrastructure.desktop.dtos.*;
import org.retrolauncher.backend.facades.*;
import org.retrolauncher.gui.modules.games.dtos.GameSearchFilter;
import org.retrolauncher.gui.modules.games.models.Game;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class LocalGamesGateway implements GamesGateway {
    private final GamesFacade facade = new GamesFacadeImpl();

    @Override
    public CompletableFuture<List<Game>> listAll(GameSearchFilter params) {
        final Supplier<List<Game>> lambda = () -> facade.listAll(new GameSearchParams(params.name()))
                .stream()
                .map((item) -> {
                    final var iconPath = item.iconPath().isPresent() ?
                            Path.of(item.iconPath().get()) : null;
                    return new Game(item.id(), item.name(), item.platformName(), iconPath);
                }).toList();
        final var executorService = Executors.newSingleThreadExecutor();
        final var result = CompletableFuture.supplyAsync(lambda, executorService);
        executorService.shutdown();
        return result;
    }

    @Override
    public CompletableFuture<Void> updateGame(Game game) {
        return handleRequestWithoutResponse(() -> {
            facade.updateGame(new UpdateGameRequestDto(game.getId(), game.getName()));
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> saveCover(Game game) {
        return handleRequestWithoutResponse(() -> {
            if (game.getIconPath().isPresent())
                facade.saveCover(new SaveGameCoverDto(game.getId().toString(), game.getIconPath().get().toFile()));
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> createShortcut(Game game) {
        return handleRequestWithoutResponse(() -> {
            facade.createShortcut(game.getId().toString());
            return null;
        });
    }

    @Override
    public CompletableFuture<Void> reindexGames() {
        return handleRequestWithoutResponse(() -> {
            facade.reindexGames();
            return null;
        });
    }

    @Override
    public void startGame(Game game) {
        new Thread(() -> facade.startGame(game.getId())).start();
    }

    private CompletableFuture<Void> handleRequestWithoutResponse(Supplier<Void> callback) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final CompletableFuture<Void> result = CompletableFuture.supplyAsync(callback, executorService);
        executorService.shutdown();
        return result;
    }
}
