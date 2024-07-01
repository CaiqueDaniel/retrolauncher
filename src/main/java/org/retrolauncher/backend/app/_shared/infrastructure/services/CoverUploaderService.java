package org.retrolauncher.backend.app._shared.infrastructure.services;

import net.sf.image4j.codec.ico.ICOEncoder;
import org.retrolauncher.backend.app._shared.application.exceptions.NotAbleToUploadCoverException;
import org.retrolauncher.backend.app._shared.application.services.UploaderService;
import org.retrolauncher.backend.app.games.application.exceptions.CoverUploadedIsNotImageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
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

        Path icoPath = null;

        try {
            icoPath = this.saveIcon(file, filename);
            return this.saveImage(file, filename);
        } catch (IOException exception) {
            if (icoPath != null)
                icoPath.toFile().delete();
            throw new NotAbleToUploadCoverException(exception);
        }
    }

    @Override
    public void remove(Path coverPath) {
        if (!coverPath.toFile().exists())
            return;

        final String coverFilename = getIconName(coverPath);
        final File cover = coverPath.toFile();
        final File coverIco = Path.of(
                coverPath.getParent().toAbsolutePath().toString(),
                coverFilename + ".ico"
        ).toFile();

        if (cover.exists()) cover.delete();
        if (coverIco.exists()) coverIco.delete();
    }

    private Path saveImage(File file, String filename) throws IOException {
        InputStream in = null;
        OutputStream out = null;

        try {
            Path path = Path.of(COVERS_DIRECTORY);
            Files.createDirectories(path);
            Path uploadedFilePath = path.resolve(filename + this.getExtension(file.getName()));

            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(new FileOutputStream(uploadedFilePath.toFile()));

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }

            in.close();
            out.close();

            return uploadedFilePath;
        } catch (IOException exception) {
            if (in != null) in.close();
            if (out != null) out.close();
            throw exception;
        }
    }

    private Path saveIcon(File file, String filename) throws IOException {
        Path path = Path.of(COVERS_DIRECTORY);
        Files.createDirectories(path);
        BufferedImage bufferedImage = ImageIO.read(file);
        File outputFile = path.resolve(filename + ".ico").toFile();
        ICOEncoder.write(bufferedImage, outputFile);
        return outputFile.toPath();
    }

    private boolean isValidFormat(File file) {
        return Arrays.stream(ACCEPTED_EXTENSIONS)
                .anyMatch((extension) -> file.getName().toLowerCase().contains(extension));
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    private String getIconName(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
}
