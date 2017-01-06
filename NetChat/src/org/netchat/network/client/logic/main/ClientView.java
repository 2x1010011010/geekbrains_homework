package org.netchat.network.client.logic.main;

public interface ClientView {

    void writeMessage(String message);

    void writeError(String error);

    void logout();
}