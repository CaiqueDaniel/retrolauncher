package org.retrolauncher.app.games.infrastructure.cli.commands;

import org.retrolauncher.app.games.application.usecases.StartGameUseCase;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "game:start",
        version = "1.0",
        description = "Inicia jogo dado um id"
)
public class StartGameCommand implements Callable<Integer> {
    private final StartGameUseCase startGameUseCase;

    @CommandLine.Parameters(index = "0", description = "O id do jogo")
    private String id;

    public StartGameCommand(StartGameUseCase startGameUseCase) {
        this.startGameUseCase = startGameUseCase;
    }

    @Override
    public Integer call() {
        this.startGameUseCase.execute(this.id);
        return 0;
    }
}
