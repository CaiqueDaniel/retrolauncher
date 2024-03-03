package unit;

import org.retrolauncher.backend.app.platforms.domain.entities.Platform;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.models.PlatformModel;
import org.retrolauncher.backend.app.platforms.infrastructure.database.jackson.mappers.JacksonPlatformMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class JacksonPlatformMapperUnitTests {
    @Test
    void itShouldBeAbleToCreateAModel() {
        Platform platform = new Platform("Nintendo 64", "path", new ArrayList<>());
        PlatformModel result = JacksonPlatformMapper.fromDomain(platform);

        assertEquals(result.getId(), platform.getId());
        assertEquals(result.getName(), platform.getName());
        assertEquals(result.getCorePath(), platform.getCorePath());
        assertEquals(result.getExtensions().size(), platform.getExtensions().size());
    }

    @Test
    void itShouldBeAbleToCreateAEntity() {
        Platform platform = new Platform("Nintendo 64", "path", new ArrayList<>());
        PlatformModel model = JacksonPlatformMapper.fromDomain(platform);
        Platform result = JacksonPlatformMapper.toDomain(model);

        assertEquals(result.getId(), platform.getId());
        assertEquals(result.getName(), platform.getName());
        assertEquals(result.getCorePath(), platform.getCorePath());
        assertEquals(result.getExtensions().size(), platform.getExtensions().size());
    }
}
