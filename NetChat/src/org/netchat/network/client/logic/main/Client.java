package org.netchat.network.client.logic.main;

public interface Client {

    void setServerHost(String host);

    void setServerPort(int port);

    void setLogin(String login);

    void setClieView(ClientView view);

    void connect() throws ClientException;

    void login(String login) throws ClientException;

    void send(String message) throws ClientException;

    void sendMessage(String message) throws ClientException;

    String receive() throws ClientException;

    void start() throws ClientException;

    void disconnect();
}
