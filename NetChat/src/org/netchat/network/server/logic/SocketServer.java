package org.netchat.network.server.logic;

import org.netchat.network.server.logic.main.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketServer implements Server, Runnable {
    private Integer port;
    private ServerSocket socket;
    private Map<String, SocketUser> users = new ConcurrentHashMap<>();

    private ServerView view;

    @Override
    public void setPort(int port) throws IllegalArgumentException {
        this.port = port;
    }

    @Override
    public void run() {
        while (!this.socket.isClosed()) {
            try {
                Socket accept = this.socket.accept();
                SocketUser user = new SocketUser();
                try {
                    user.setSocket(accept);
                    user.init();
                    String login = user.receiveLogin();
                    if (login == null || login.isEmpty() || this.users.containsKey(login)) {
                        user.sendError("invalid login");
                        user.close();
                        continue;
                    } else {
                        user.sendMessage("ok");
                    }
                    user.setLogin(login);
                    user.setId(SocketUser.nextId());
                    user.setSocketServer(this);
                    this.addUser(user);
                    Thread userThread = new Thread(user);
                    userThread.start();
                } catch (ServerException e) {
                    e.printStackTrace();
                    user.close();
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void turnOn() throws ServerException {
        if (this.port == null) {
            throw new ServerException("empty port");
        }
        try {
            this.socket = new ServerSocket(this.port);
            Thread thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            throw new ServerException(String.format("can't turnOn server by port=[%s]", this.port), e);
        }
    }

    @Override
    public void turnOff() throws ServerException {
        if (this.socket == null) {
            throw new ServerException("servet not turnOn");
        }
        try {
            for (SocketUser user : this.users.values()) {
                user.close();
                if (this.view != null) {
                    this.view.removeUser(user);
                }
            }
            this.socket.close();
        } catch (IOException e) {
            throw new ServerException(String.format("problem while turnOff server"), e);
        }
    }

    public void send(String message, SocketUser user) {
        if (user == null) {
            return;
        }
        try {
            user.sendMessage(message);
        } catch (ConnectionCloseException e) {
            e.printStackTrace();
            user.close();
            removeUser(user);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void sendAll(String message) {
        for (SocketUser user : this.users.values()) {
            send(message, user);
        }
        if (this.view != null) {
            this.view.writeMessage(message);
        }
    }

    @Override
    public void sendAll(String message, User except) {
        for (SocketUser user : this.users.values()) {
            if (!user.equals(except)) {
                send(message, user);
            }
        }
        if (this.view != null) {
            this.view.writeMessage(message);
        }
    }

    @Override
    public void addUser(User user) {
        sendAll(String.format("Add new user=[%s]", user.getLogin()));
        this.users.put(user.getLogin(), (SocketUser) user);
        if (this.view != null) {
            this.view.addUser(user);
        }
    }

    @Override
    public void removeUser(User user) {
        this.users.remove(user.getLogin());
        sendAll(String.format("User=[%s] left", user.getLogin()));
        if (this.view != null) {
            this.view.removeUser(user);
        }
    }

    @Override
    public void setServerView(ServerView view) {
        this.view = view;
    }
}
