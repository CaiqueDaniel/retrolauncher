package org.retrolauncher.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.retrolauncher.app._shared.infrastructure.database.jackson.model.Model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JacksonFileDatabaseDriver<M extends Model> implements FileDatabaseDriver<Map<String, M>> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void write(Map<String, M> content, String path) {
        File storage = this.openStorage(path);

        try {
            JacksonFileDatabaseDriver.mapper.writeValue(storage, content);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Map<String, M> read(String path) {
        File storage = this.openStorage(path);

        try {
            return JacksonFileDatabaseDriver.mapper.readValue(
                    storage,
                    new TypeReference<>() {
                    }
            );
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void clear(String path) {
        File file = this.openStorage(path);
        if (file.exists())
            file.delete();
    }

    private File openStorage(String path) throws RuntimeException {
        File file = new File(path);

        try {
            ObjectMapper mapper = new ObjectMapper();

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            if (file.length() == 0)
                mapper.writeValue(file, new HashMap<>());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        return file;
    }
}
