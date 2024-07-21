package org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.models;

import jakarta.persistence.*;
import lombok.*;
import org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models.GameModel;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "platforms")
public class PlatformModel {
    @Id
    UUID id;

    @Column
    String name;

    @Column
    String corePath;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "platform")
    List<GameModel> games;
}
