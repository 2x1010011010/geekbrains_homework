package org.netchat.network.server.logic.main;

public class ServerException extends Exception {
    public ServerException() {
    }

    public ServerException(final String message) {
        super(message);
    }

    public ServerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServerException(final Throwable cause) {
        super(cause);
    }
}