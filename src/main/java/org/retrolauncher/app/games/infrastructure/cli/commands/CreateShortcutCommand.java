package org.retrolauncher.app.games.infrastructure.cli.commands;

import org.retrolauncher.app.games.application.usecases.CreateGameShortcutUseCase;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "shortcut:create",
        version = "1.0",
        description = "Cria atalhos para um jogo dado um id"
)
public class CreateShortcutCommand implements Callable<Integer> {
    private final CreateGameShortcutUseCase createGameShortcutUseCase;

    @CommandLine.Parameters(index = "0", description = "O id do jogo")
    private String id;

    public CreateShortcutCommand(CreateGameShortcutUseCase createGameShortcutUseCase) {
        this.createGameShortcutUseCase = createGameShortcutUseCase;
    }

    @Override
    public Integer call() {
        this.createGameShortcutUseCase.execute(this.id);
        return 0;
    }
}
