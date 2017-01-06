package org.netchat.network.client.logic;

import org.netchat.network.client.logic.main.Client;
import org.netchat.network.client.logic.main.ClientException;
import org.netchat.network.client.logic.main.ClientView;
import org.netchat.network.client.logic.main.ConnectionCloseException;
import org.netchat.network.common.Message;
import org.netchat.network.common.MessageUtil;

import java.io.*;
import java.net.Socket;

public class SocketClient implements Client, Runnable {
    private String host;
    private Integer port;
    private String login;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private ClientView view;

    @Override
    public void setServerHost(String host) {
        this.host = host;
    }

    @Override
    public void setServerPort(int port) {
        this.port = port;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void connect() throws ClientException {
        if (this.host == null || this.host.isEmpty() || this.port == null) {
            throw new ClientException(String.format("try connection by host=[%s] port=[%s]", this.host, this.port));
        }
        try {
            this.socket = new Socket(this.host, this.port);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (Throwable t) {
            throw new ClientException("problem while connect to server", t);
        }
    }

    @Override
    public void login(String login) throws ClientException {
        if (this.socket == null) {
            throw new ClientException("socket is empty");
        }
        Message sendMessage = new Message();
        sendMessage.setMsg(MessageUtil.LOGIN_MSG);
        sendMessage.setText(login);
        send(MessageUtil.createMessage(sendMessage));

        Message receiveMessage = MessageUtil.readMessage(receive());
        if (MessageUtil.ERROR_MSG.equals(receiveMessage.getMsg())) {
            throw new ClientException(receiveMessage.getText());
        }
        if (MessageUtil.MESSAGE_MSG.equals(receiveMessage.getMsg())) {
            if (!"ok".equals(receiveMessage.getText())) {
                throw new ClientException(String.format("from server=[%s]", receiveMessage.getText()));
            }
            return;
        }
        throw new ClientException("not login");
    }

    @Override
    public void setClieView(ClientView view) {
        this.view = view;
    }

    @Override
    public void run() {
        while (!this.socket.isClosed()) {
            try {
                Message message = MessageUtil.readMessage(receive());
                if (this.view != null) {
                    if (MessageUtil.ERROR_MSG.equals(message.getMsg())) {
                        this.view.writeError(String.format("From server: %s", message.getText()));
                    } else if (MessageUtil.MESSAGE_MSG.equals(message.getMsg())) {
                        this.view.writeMessage(message.getText());
                    }
                }
            } catch (ConnectionCloseException e) {
                e.printStackTrace();
                this.disconnect();
                if (this.view != null) {
                    this.view.logout();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(final String message) throws ClientException {
        if (this.socket == null) {
            throw new ClientException("socket is empty");
        }
        Message m = new Message();
        m.setMsg(MessageUtil.MESSAGE_MSG);
        m.setText(String.format("%s:%s", this.login, message));
        try {
            send(MessageUtil.createMessage(m));
        } catch (ConnectionCloseException e) {
            e.printStackTrace();
            this.disconnect();
            if (this.view != null) {
                this.view.logout();
            }
        }
    }

    @Override
    public void send(String message) throws ClientException {
        if (this.socket == null) {
            throw new ClientException("socket is empty");
        }
        try {
            this.writer.write(String.format("%s\n", message));
            this.writer.flush();
        } catch (IOException e) {
            throw new ConnectionCloseException("error while send message", e);
        } catch (Throwable t) {
            throw new ClientException("error while send message", t);
        }
    }

    @Override
    public String receive() throws ClientException {
        if (this.socket == null) {
            throw new ClientException("socket is empty");
        }
        try {
            String line = this.reader.readLine();
            if (line == null) {
                throw new IOException("connection lose");
            }
            return line;
        } catch (IOException e) {
            throw new ConnectionCloseException("error while send message", e);
        } catch (Throwable t) {
            throw new ClientException("error while send message", t);
        }
    }

    @Override
    public void start() throws ClientException {
        if (this.socket == null) {
            throw new ClientException("socket is empty");
        }
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void disconnect() {
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
