package org.retrolauncher.backend.app._shared.infrastructure.services;

import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToUploadCoverException;
import org.retrolauncher.backend.app._shared.application.services.UploaderService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class CoverUploaderService implements UploaderService {
    private final static String COVERS_DIRECTORY = new StringBuilder(System.getProperty("user.home"))
            .append("/retro-launcher")
            .append("/covers")
            .toString();

    @Override
    public String upload(File file) {
        try {
            Files.createDirectories(Path.of(COVERS_DIRECTORY));
            String uploadedFilePath = new StringBuilder(COVERS_DIRECTORY)
                    .append('/')
                    .append(UUID.randomUUID())
                    .append(this.getExtension(file.getName()))
                    .toString();
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(uploadedFilePath));

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
            return uploadedFilePath;
        } catch (IOException exception) {
            throw new NotAbleToUploadCoverException(exception);
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
