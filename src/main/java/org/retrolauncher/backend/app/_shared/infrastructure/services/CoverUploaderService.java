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
    private static final String[] ACCEPTED_EXTENSIONS = {".png", ".jpg", ".jpeg"};

    private static final String COVERS_DIRECTORY = Path.of(System.getProperty("user.home"))
            .resolve("retro-launcher")
            .resolve("covers")
            .toString();

    @Override
    public Path upload(File file) {
        return this.upload(file, UUID.randomUUID().toString());
    }

    @Override
    public Path upload(File file, String filename) {
        if (!this.isValidFormat(file))
            throw new CoverUploadedIsNotImageException();

        try {
            this.saveIcon(file, filename);
            return this.saveImage(file, filename);
        } catch (IOException exception) {
            throw new NotAbleToUploadCoverException(exception);
        }
    }

    private Path saveImage(File file, String filename) throws IOException {
        Path path = Path.of(COVERS_DIRECTORY);
        Files.createDirectories(path);
        Path uploadedFilePath = path.resolve(filename + this.getExtension(file.getName()));
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(uploadedFilePath.toFile()));

        byte[] buffer = new byte[1024];
        int lengthRead;
        while ((lengthRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, lengthRead);
            out.flush();
        }
        return uploadedFilePath;
    }

    private void saveIcon(File file, String filename) throws IOException {
        Path path = Path.of(COVERS_DIRECTORY);
        Files.createDirectories(path);
        BufferedImage bufferedImage = ImageIO.read(file);
        File outputFile = path.resolve(filename + ".ico").toFile();
        ICOEncoder.write(bufferedImage, outputFile);
    }

    private boolean isValidFormat(File file) {
        return Arrays.stream(ACCEPTED_EXTENSIONS)
                .anyMatch((extension) -> file.getName().contains(extension));
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
