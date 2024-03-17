package org.retrolauncher.backend.app._shared.infrastructure.services;

import net.sf.image4j.codec.ico.ICOEncoder;
import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToUploadCoverException;
import org.retrolauncher.backend.app._shared.application.services.UploaderService;
import org.retrolauncher.backend.app.games.application.exceptions.CoverUploadedIsNotImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

public class CoverUploaderService implements UploaderService {
    private final static String[] ACCEPTED_EXTENSIONS = {"png", "jpg", "jpeg"};

    private final static String COVERS_DIRECTORY = new StringBuilder(System.getProperty("user.home"))
            .append("/retro-launcher")
            .append("/covers")
            .toString();

    @Override
    public String upload(File file) {
        return this.upload(file, UUID.randomUUID().toString());
    }

    @Override
    public String upload(File file, String filename) {
        if (this.isValidFormat(file))
            throw new CoverUploadedIsNotImageException();

        try {
            Files.createDirectories(Path.of(COVERS_DIRECTORY));
            String uploadedFilePath = new StringBuilder(COVERS_DIRECTORY)
                    .append('\\')
                    .append(filename)
                    .append(".ico")
                    .toString();
            BufferedImage bufferedImage = ImageIO.read(file);
            File outputFile = new File(uploadedFilePath);
            ICOEncoder.write(bufferedImage, outputFile);

            return uploadedFilePath;
        } catch (IOException exception) {
            throw new NotAbleToUploadCoverException(exception);
        }
    }

    private boolean isValidFormat(File file) {
        return Arrays.stream(ACCEPTED_EXTENSIONS)
                .anyMatch((extension) -> this.getExtension(file.getName()).contains(extension));
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
