package org.retrolauncher.backend.app.settings.infrastructure.database.jackson.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SettingModel {
    @JsonProperty
    private String romsFolderPath;

    @JsonProperty
    private String retroarchFolderPath;
}
