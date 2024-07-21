package org.retrolauncher.backend.app.platforms.infrastructure.database.hibernate.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "platform")
public class PlatformModel {
    @Id
    UUID id;
    String name;
    String corePath;
    List<String> extensions = new ArrayList<>();
}
