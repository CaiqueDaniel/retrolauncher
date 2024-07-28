package org.retrolauncher.gui.router;

import org.retrolauncher.gui.base.Page;
import org.retrolauncher.gui.config.DependencyInjector;
import org.retrolauncher.gui.config.Renderer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Router {
    private final Map<Routes, Page> routes = new HashMap<>();
    private static Router instance;

    private Router() {
        routes.put(Routes.GAMES, DependencyInjector.getInstance().gamesPage());
        routes.put(Routes.SETTINGS, DependencyInjector.getInstance().settingsPage());
    }

    public void navigateTo(Routes path) {
        Optional<Page> page = Optional.ofNullable(routes.get(path));
        if (page.isEmpty()) return;
        this.handlePageChange(page.get());
    }

    private void handlePageChange(Page page) {
        try {
            Renderer.getInstance().render(page);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar essa página");
            throw new RuntimeException(exception);
        }
    }

    public static Router getInstance() {
        if (instance == null)
            instance = new Router();
        return instance;
    }
}

