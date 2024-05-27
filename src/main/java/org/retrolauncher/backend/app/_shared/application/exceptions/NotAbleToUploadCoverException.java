package org.retrolauncher.backend.app._shared.application.exceptions;

public class NotAbleToUploadCoverException extends RuntimeException {
    private static final String MESSAGE = "Unable to upload cover";

    public NotAbleToUploadCoverException(Exception cause) {
        super(MESSAGE, cause);
    }
}
