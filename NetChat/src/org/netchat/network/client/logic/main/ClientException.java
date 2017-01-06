package org.netchat.network.client.logic.main;

public class ClientException extends Exception {
    public ClientException() {
    }

    public ClientException(final String message) {
        super(message);
    }

    public ClientException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ClientException(final Throwable cause) {
        super(cause);
    }
}