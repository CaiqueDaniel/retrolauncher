package org.retrolauncher.backend.app.games.infrastructure.desktop.dtos;

import org.retrolauncher.backend.app.games.application.dtos.UpdateGameInputDto;

import java.util.UUID;

public class UpdateGameRequestDto extends UpdateGameInputDto {
    public UpdateGameRequestDto(UUID id, String name) {
        super(id, name);
    }
}
