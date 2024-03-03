package org.retrolauncher.backend.database;

import org.retrolauncher.backend.app._shared.infrastructure.database.jackson.model.Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MemoryFileDatabaseDriver<M extends Model> implements FileDatabaseDriver<Map<String, M>> {

    private Map<String, M> storedData = new HashMap<>();

    @Override
    public void write(Map<String, M> content, String path) {
        this.storedData = content;
    }

    @Override
    public Map<String, M> read(String path) {
        return this.storedData;
    }

    @Override
    public void clear(String path) throws IOException {
        this.storedData.clear();
    }
}
