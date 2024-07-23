package org.retrolauncher.backend.app.games.application.services;

import java.io.File;
import java.util.List;

public interface GameFinderService {
    List<File> getFilesFrom(File folder);
}
