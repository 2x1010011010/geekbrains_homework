package org.netchat.network.server.logic.main;

public interface ServerView {
    void addUser(User user);

    void removeUser(User user);

    void writeMessage(String msg);

    void writeError(String msg);
}
