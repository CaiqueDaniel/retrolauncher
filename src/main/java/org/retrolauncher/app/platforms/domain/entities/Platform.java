package org.retrolauncher.app.platforms.domain.entities;

import lombok.Getter;
import org.retrolauncher.app._shared.domain.entities.Entity;

import java.util.List;
import java.util.UUID;

@Getter
public class Platform extends Entity {
    private final String name;
    private final String corePath;
    private final List<String> extensions;

    public Platform(String name, String corePath, List<String> extensions) {
        super(UUID.randomUUID());

        this.name = name;
        this.corePath = corePath;
        this.extensions = extensions;
    }

    public Platform(UUID id, String name, String corePath, List<String> extensions) {
        super(id);

        this.name = name;
        this.corePath = corePath;
        this.extensions = extensions;
    }
}
