package org.retrolauncher.gui.contexts;

import org.retrolauncher.gui.models.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SelectedGameContext {
    private static final List<Consumer<Game>> subscribers = new ArrayList<>();

    public static void dispatch(Game game) {
        subscribers.forEach((callback) -> {
            try {
                callback.accept(game);
            } catch (Exception e) {
                e.getStackTrace();
            }
        });
    }

    public static void subscribe(Consumer<Game> callback) {
        subscribers.add(callback);
    }
}
