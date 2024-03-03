package org.retrolauncher.backend.config;

import org.retrolauncher.backend.app.games.infrastructure.cli.commands.CreateShortcutCommand;
import org.retrolauncher.backend.app.games.infrastructure.cli.commands.ListGamesCommand;
import org.retrolauncher.backend.app.games.infrastructure.cli.commands.StartGameCommand;
import picocli.CommandLine;

import java.util.*;

public class CommandsHandler implements Runnable {
    private final Deque<String> args;
    private final List<CommandLine> commands;

    public CommandsHandler(DependencyInjector dependencyInjector, String[] args) {
        this.args = new LinkedList<>(List.of(args));
        this.commands = List.of(
                new CommandLine(new ListGamesCommand(dependencyInjector.getListGamesUseCase())),
                new CommandLine(new CreateShortcutCommand(dependencyInjector.getCreateGameShortcutUseCase())),
                new CommandLine(new StartGameCommand(dependencyInjector.getStartGameUseCase()))
        );
    }

    @Override
    public void run() {
        if (this.args.size() == 0)
            return;

        String commandName = this.args.removeFirst();
        Optional<CommandLine> foundCommand = this.commands.stream()
                .filter((command) -> command
                        .getCommandName()
                        .equals(commandName))
                .findFirst();

        if (foundCommand.isEmpty())
            return;

        foundCommand.get().execute(this.args.toArray(String[]::new));
    }
}
