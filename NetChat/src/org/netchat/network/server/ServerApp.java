package org.netchat.network.server;

import org.netchat.network.common.history.FileHistoryWrapper;
import org.netchat.network.server.logic.SocketServer;
import org.netchat.network.server.view.ServerForm;

public class ServerApp {
    public static void main(String[] args) {
        FileHistoryWrapper historyWrapper = new FileHistoryWrapper();
        historyWrapper.init("C:/test/server_history.txt");

        SocketServer server = new SocketServer();

        ServerForm serverForm = new ServerForm(historyWrapper, server);
        server.setServerView(serverForm);

        serverForm.setVisible(true);
    }
}
