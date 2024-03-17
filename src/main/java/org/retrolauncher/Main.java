package org.retrolauncher;

import javafx.application.Application;
import org.retrolauncher.backend.Backend;
import org.retrolauncher.gui.GUI;

public class Main {
    public static void main(String[] args) {
        Backend.main(args);

        if (args.length == 0)
            Application.launch(GUI.class);
    }
}