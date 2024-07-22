package org.retrolauncher.gui.modules.games.dtos;

import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(fluent = true)
public class GameSearchFilter {
    private String name = "";
}
