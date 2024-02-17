package org.retrolauncher.app.games.application.exceptions;

public class ShortcutNotCreatedException extends RuntimeException {
    private static final String MESSAGE = "It was not possible to create a shortcut for this game. Verify if you are running this command with admin privileges.";

    public ShortcutNotCreatedException() {
        super(ShortcutNotCreatedException.MESSAGE);
    }

    public ShortcutNotCreatedException(Exception exception) {
        super(ShortcutNotCreatedException.MESSAGE, exception);
    }
}
