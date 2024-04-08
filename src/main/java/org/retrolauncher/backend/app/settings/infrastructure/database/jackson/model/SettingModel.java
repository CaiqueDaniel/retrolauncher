package org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.retrolauncher.backend.app._shared.infrastructure.database.jackson.model.Model;

@Getter
@Setter
@NoArgsConstructor
public class SettingModel extends Model {
    @JsonProperty
    private String romsFolderPath;

    @JsonProperty
    private String retroarchFolderPath;
}
