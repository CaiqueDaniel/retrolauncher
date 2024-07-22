package org.retrolauncher.backend.app.games.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Accessors(fluent = true)
public class GameSearchParams {
    private String name = "";
}
