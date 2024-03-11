package org.retrolauncher;

import javafx.application.Application;
import org.retrolauncher.backend.Backend;
import org.retrolauncher.backend.config.DependencyInjector;
import org.retrolauncher.gui.GUI;

public class Main {
    private static final DependencyInjector dependencyInjector = new DependencyInjector();

    public static void main(String[] args) {
        Backend.main(args);
        Application.launch(GUI.class);
    }
}