package org.retrolauncher.backend.app.games.application.dtos;

import java.util.UUID;

public class UpdateGameInputDto {
    public final UUID id;
    public final String name;

    public UpdateGameInputDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
