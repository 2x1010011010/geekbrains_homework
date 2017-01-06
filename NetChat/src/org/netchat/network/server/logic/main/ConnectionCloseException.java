package org.netchat.network.server.logic.main;

public class ConnectionCloseException extends ServerException {

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
