package org.retrolauncher.database;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.retrolauncher.app._shared.infrastructure.database.jackson.model.Model;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JacksonFileDatabaseDriver<M extends Model> implements FileDatabaseDriver<Map<String, M>> {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Class<M> modelType;

    public JacksonFileDatabaseDriver(Class<M> modelType) {
        this.modelType = modelType;
    }

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
        JavaType type = JacksonFileDatabaseDriver.mapper.getTypeFactory().
                constructMapType(HashMap.class, String.class, this.modelType);

        try {
            return JacksonFileDatabaseDriver.mapper.readValue(storage, type);
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
