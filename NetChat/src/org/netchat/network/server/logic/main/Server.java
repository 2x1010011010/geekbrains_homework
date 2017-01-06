package org.netchat.network.server.logic.main;

public interface Server {
    void setPort(int port) throws IllegalArgumentException;

    void turnOn() throws ServerException;

    void turnOff() throws ServerException;

    void sendAll(String message);

    void sendAll(String message, User except);

    void addUser(User user);

    void removeUser(User user);

    void setServerView(ServerView view);
}
