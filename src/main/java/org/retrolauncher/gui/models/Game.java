package org.retrolauncher.gui.models;

import java.util.Optional;

public record Game(String id, String name, Optional<String> iconPath) {
}
