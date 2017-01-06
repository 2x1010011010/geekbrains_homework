package org.netchat.network.client;

import org.netchat.network.client.logic.SocketClient;
import org.netchat.network.client.view.ChatClientForm;
import org.netchat.network.client.view.ChatLoginForm;
import org.netchat.network.common.history.FileHistoryWrapper;

public class ClientApp {
    public static void main(String[] args) {
        FileHistoryWrapper historyWrapper = new FileHistoryWrapper();
        historyWrapper.init("C:/test/client_history.txt");

        SocketClient client = new SocketClient();

        ChatClientForm clientForm = new ChatClientForm(historyWrapper, client);
        ChatLoginForm loginForm = new ChatLoginForm(client, clientForm);

        client.setClieView(clientForm);

        clientForm.setLoginFrame(loginForm);
        loginForm.setVisible(true);
    }
}