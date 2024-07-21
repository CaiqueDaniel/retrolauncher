package org.retrolauncher.backend.app.games.infrastructure.database.hibernate.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity()
@Table(name = "games")
public class GameModel {
    @Id
    UUID id;
    String platformId;
    String name;
    String path;
    String iconPath;
}
