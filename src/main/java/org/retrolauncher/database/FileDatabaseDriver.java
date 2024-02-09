package org.retrolauncher.database;

import java.io.IOException;

public interface FileDatabaseDriver<T> {
    void write(T content, String path) throws IOException;

    T read(String path) throws IOException;

    void clear(String path) throws IOException;
}
