package org.retrolauncher.app._shared.infrastructure.database.jackson.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public abstract class Model {
    @JsonProperty
    protected UUID id;
}
