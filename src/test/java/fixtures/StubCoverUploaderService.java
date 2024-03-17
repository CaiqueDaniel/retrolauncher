package fixtures;

import org.retrolauncher.backend.app._shared.application.services.UploaderService;

import java.io.File;
import java.util.UUID;

public class StubCoverUploaderService implements UploaderService {
    @Override
    public String upload(File file) {
        return this.upload(file, UUID.randomUUID().toString());
    }

    @Override
    public String upload(File file, String filename) {
        return new StringBuilder("test")
                .append('\\')
                .append(filename)
                .append(".ico")
                .toString();
    }
}
