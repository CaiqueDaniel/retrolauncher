package org.retrolauncher.backend.app.games.infrastructure.cli.commands;

import org.retrolauncher.backend.app.games.application.usecases.ListGamesUseCase;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "games:list",
        description = "Lista todos os jogos indexados")
public class ListGamesCommand implements Callable<Integer> {
    private final ListGamesUseCase listGamesUseCase;

    public ListGamesCommand(ListGamesUseCase listGamesUseCase) {
        this.listGamesUseCase = listGamesUseCase;
    }

    @Override
    public Integer call() {
        this.listGamesUseCase.execute().forEach((game) -> {
            String output = new StringBuilder(game.id()).append("\t\t").append(game.name()).toString();
            System.out.println(output);
        });

        return 0;
    }
}
