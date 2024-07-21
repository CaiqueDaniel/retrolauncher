package org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.models.PlatformModel;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity()
@Table(name = "games")
public class GameModel {
    @Id
    UUID id;

    @Column(insertable = false, updatable = false)
    UUID platformId;

    @Column
    String name;

    @Column
    String path;

    @Column
    String iconPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platformId", referencedColumnName = "id")
    PlatformModel platform;
}
