package org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.retrolauncher.backend.app._shared.infrastructure.database.jackson.model.Model;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlatformModel extends Model {
    @JsonProperty
    private String name;

    @JsonProperty
    private String corePath;

    @JsonProperty
    private List<String> extensions = new ArrayList<>();
}
