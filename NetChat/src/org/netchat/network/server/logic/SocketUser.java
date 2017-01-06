package org.netchat.network.server.logic;

import org.netchat.network.common.Message;
import org.netchat.network.common.MessageUtil;
import org.netchat.network.server.logic.main.ConnectionCloseException;
import org.netchat.network.server.logic.main.ServerException;
import org.netchat.network.server.logic.main.User;

import java.io.*;
import java.net.Socket;

public class SocketUser extends User implements Runnable {
    private static long ID = 0;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private SocketServer socketServer;

    public SocketUser() {
        super();
    }

    public void init() throws ServerException {
        if (this.socket == null) {
            throw new ServerException("empty client socket");
        }
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Throwable e) {
            throw new ServerException("can't set connection", e);
        }
    }

    public String receiveLogin() throws ServerException {
        Message message = receiveMessage();
        return MessageUtil.LOGIN_MSG.equals(message.getMsg()) ? message.getText() : null;
    }

    public void sendError(String messge) throws ServerException {
        Message message = new Message();
        message.setMsg(MessageUtil.ERROR_MSG);
        message.setText(messge);
        sendMessage(message);
    }

    public void sendMessage(String messge) throws ServerException {
        Message message = new Message();
        message.setMsg(MessageUtil.MESSAGE_MSG);
        message.setText(messge);
        sendMessage(message);
    }

    public Message receiveMessage() throws ServerException {
        if (this.socket == null) {
            throw new ServerException("empty client socket");
        }
        try {
            String line = this.reader.readLine();
            if (line == null) {
                throw new IOException("close connection");
            }
            return MessageUtil.readMessage(line);
        } catch (IOException ioe) {
            throw new ConnectionCloseException("error while receive message", ioe);
        } catch (Throwable e) {
            throw new ServerException("error while receive message", e);
        }
    }

    public void sendMessage(Message message) throws ServerException {
        if (this.socket == null) {
            throw new ServerException("empty client socket");
        }
        try {
            this.writer.write(String.format("%s\n", MessageUtil.createMessage(message)));
            this.writer.flush();
        } catch (IOException ioe) {
            throw new ConnectionCloseException("error while send message", ioe);
        } catch (Throwable e) {
            throw new ServerException("error while send message", e);
        }
    }

    @Override
    public void run() {
        while (!this.socket.isClosed()) {
            try {
                Message message = receiveMessage();
                if (MessageUtil.MESSAGE_MSG.equals(message.getMsg())) {
                    if (this.socketServer != null) {
                        this.socketServer.sendAll(message.getText(), this);
                    }
                }
            } catch (ConnectionCloseException e) {
                e.printStackTrace();
                this.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        this.socketServer.removeUser(this);
    }

    public void close() {
        if (this.socket != null) {
            try {
                this.socket.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static synchronized long nextId() {
        return ++ID;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public SocketServer getSocketServer() {
        return this.socketServer;
    }

    public void setSocketServer(SocketServer socketServer) {
        this.socketServer = socketServer;
    }
}
