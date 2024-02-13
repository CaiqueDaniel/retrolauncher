package org.retrolauncher.app.games.infrastructure.database.jackson.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GameModel {
    @JsonProperty
    private UUID id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String path;

    @JsonProperty
    private String iconPath;

    @JsonProperty
    private String platformId;
}
