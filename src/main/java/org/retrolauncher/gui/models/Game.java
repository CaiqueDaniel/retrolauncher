package org.retrolauncher.gui.models;

import java.nio.file.Path;
import java.util.Optional;

public record Game(String id, String name, Optional<Path> iconPath) {
}
