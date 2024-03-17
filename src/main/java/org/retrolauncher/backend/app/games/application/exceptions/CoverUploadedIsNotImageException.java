package org.retrolauncher.backend.app.games.application.exceptions;

public class CoverUploadedIsNotImageException extends RuntimeException {
    private static final String MESSAGE = "Uploaded cover is not a image";

    public CoverUploadedIsNotImageException() {
        super(MESSAGE);
    }
}
