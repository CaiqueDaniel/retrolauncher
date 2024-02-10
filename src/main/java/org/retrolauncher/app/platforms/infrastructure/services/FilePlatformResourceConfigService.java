package org.retrolauncher.app.platforms.infrastructure.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.retrolauncher.app.platforms.application.dtos.PlatformConfigDto;
import org.retrolauncher.app.platforms.application.services.PlatformsResourceConfigService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FilePlatformResourceConfigService implements PlatformsResourceConfigService {
    @Override
    public List<PlatformConfigDto> load() {
        try {
            String resource = "platforms.json";
            InputStream stream = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(resource);

            if (stream == null)
                return new ArrayList<>();

            return new ObjectMapper().readValue(stream.readAllBytes(), new TypeReference<>() {
            });
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
