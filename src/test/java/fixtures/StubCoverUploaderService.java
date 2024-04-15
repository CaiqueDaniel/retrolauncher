package fixtures;

import org.retrolauncher.backend.app._shared.application.services.UploaderService;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class StubCoverUploaderService implements UploaderService {
    @Override
    public Path upload(File file) {
        return this.upload(file, UUID.randomUUID().toString());
    }

    @Override
    public Path upload(File file, String filename) {
        return Path.of(new StringBuilder("test")
                .append('\\')
                .append(filename)
                .append(".png")
                .toString());
    }
}
