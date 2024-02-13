package org.retrolauncher.app.games.infrastructure.database.jackson.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.retrolauncher.app._shared.infrastructure.database.jackson.model.Model;

@Getter
@Setter
@NoArgsConstructor
public class GameModel extends Model {
    @JsonProperty
    private String name;

    @JsonProperty
    private String path;

    @JsonProperty
    private String iconPath;

    @JsonProperty
    private String platformId;
}
