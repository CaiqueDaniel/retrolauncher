package org.retrolauncher.backend.app.games.infrastructure.services;

import org.retrolauncher.backend.app.games.application.services.GameFinderService;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class PSXGameFinderService implements GameFinderService {
    @Override
    public List<File> getFilesFrom(File folder) {
        final var files = folder.listFiles();
        final var identifiedFiles = new ArrayList<File>();

        if (files == null)
            return new ArrayList<>();

        for (var file : files) {
            if (!isCUEFile(file)) continue;

            final var binFile = getBinFileFrom(file);

            if (binFile.isEmpty()) continue;
            if (!isPSXFile(binFile.get())) continue;

            identifiedFiles.add(file);
        }

        return identifiedFiles;
    }

    private boolean isCUEFile(File file) {
        return file.getName().contains(".cue");
    }

    private Optional<File> getBinFileFrom(File cueFile) {
        final var pattern = Pattern.compile("FILE \"([^\"]+)\" BINARY");
        final var binFileName = searchForContentOnFile(cueFile, pattern);

        if (binFileName.isEmpty()) return Optional.empty();

        final var binFile = Path.of(cueFile.getParent()).resolve(binFileName.get()).toFile();

        return Optional.ofNullable(binFile.exists() ? binFile : null);
    }

    private boolean isPSXFile(File binFile) {
        return searchForContentOnFile(binFile, Pattern.compile("(1993-1997 Sony)")).isPresent();
    }

    private Optional<String> searchForContentOnFile(File file, Pattern pattern) {
        String line;
        FileInputStream inputStream = null;
        String result = null;

        try {
            inputStream = new FileInputStream(file);
            final BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = buffer.readLine()) != null) {
                final var matcher = pattern.matcher(line);

                if (matcher.find()) {
                    result = matcher.group(1);
                    break;
                }
            }
        } catch (IOException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, exception.getMessage());
        }

        closeInputStream(inputStream);

        return Optional.ofNullable(result);
    }

    private void closeInputStream(InputStream inputStream) {
        if (inputStream == null)
            return;

        try {
            inputStream.close();
        } catch (IOException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, exception.getMessage());
        }
    }
}
