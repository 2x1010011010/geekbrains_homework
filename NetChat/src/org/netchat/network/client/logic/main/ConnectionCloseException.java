package org.netchat.network.client.logic.main;

public class ConnectionCloseException extends ClientException {
    public ConnectionCloseException() {
    }

    public ConnectionCloseException(final String message) {
        super(message);
    }

    public ConnectionCloseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ConnectionCloseException(final Throwable cause) {
        super(cause);
    }
}