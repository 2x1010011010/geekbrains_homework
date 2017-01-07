package org.netchat.network.client.view;

import org.netchat.network.client.logic.main.Client;
import org.netchat.network.client.logic.main.ClientException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

public class ChatLoginForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel infoLabel;
    private JButton conectButton;

    private Client client;

    public ChatLoginForm(Client client, JFrame clientFrame) throws HeadlessException {
        this.client = client;

        setTitle("Chat login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(270, 190);
        setLocationRelativeTo(null); //создаст окно на середине
        setResizable(false);
        setLayout(new BorderLayout());

        Font font = new Font("Arial", Font.PLAIN, 24);

        this.infoLabel = new JLabel("ENTER CHAT ROOM");
        this.infoLabel.setFont(font);

        JPanel panel = new JPanel(new FlowLayout());
        JLabel loginLabel = new JLabel("Login: ");
        JLabel serverLabel = new JLabel("Server:");
        JTextField loginTextField = new JTextField();
        JTextField serverTextField = new JTextField();

        loginLabel.setFont(font);
        serverLabel.setFont(font);

        loginTextField.setFont(font);
        loginTextField.setColumns(8);
        serverTextField.setFont(font);
        serverTextField.setColumns(8);

        panel.add(loginLabel);
        panel.add(loginTextField);
        panel.add(serverLabel);
        panel.add(serverTextField);

        JPanel panel1 = new JPanel(new BorderLayout());
        this.conectButton = new JButton("CONNECT");
        JLabel errorLabel = new JLabel("");

        errorLabel.setFont(font);
        errorLabel.setVisible(false);
        errorLabel.setBackground(Color.RED);

        panel1.add(this.conectButton, BorderLayout.NORTH);
        panel1.add(errorLabel, BorderLayout.CENTER);

        add(this.infoLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(panel1, BorderLayout.SOUTH);

        JFrame loginFrame = this;

        this.conectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                errorLabel.setVisible(false);
                String login = loginTextField.getText();
                String server = serverTextField.getText();
                if (login == null || (login = login.trim()).isEmpty()) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Empty login");
                    return;
                }
                if (server == null || (server = server.trim()).isEmpty()) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Empty server");
                    return;
                }
                StringTokenizer tokenizer = new StringTokenizer(server, ":");
                if (tokenizer.countTokens() != 2) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Server have to host:port");
                    return;
                }
                String host = tokenizer.nextToken().trim();
                String portString = tokenizer.nextToken().trim();
                for (char c : portString.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        errorLabel.setVisible(true);
                        errorLabel.setText("Server port - integer");
                        return;
                    }
                }
                Integer port = Integer.valueOf(portString);
                if (client == null) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Client not set");
                    return;
                }
                try {
                    client.setServerHost(host);
                    client.setServerPort(port);
                    client.connect();
                } catch (ClientException ce) {
                    ce.printStackTrace();
                    errorLabel.setVisible(true);
                    errorLabel.setText("Can't connect to server");
                    client.disconnect();
                    return;
                }
                try {
                    client.login(login);
                } catch (ClientException ce) {
                    ce.printStackTrace();
                    errorLabel.setVisible(true);
                    errorLabel.setText("Invalid login");
                    client.disconnect();
                    return;
                }
                if (clientFrame != null) {
                    try {
                        client.setLogin(login);
                        client.start();
                    } catch (ClientException e1) {
                        errorLabel.setVisible(true);
                        errorLabel.setText("Can't start");
                        e1.printStackTrace();
                        return;
                    }
                    clientFrame.setVisible(true);
                    loginFrame.setVisible(false);
                }
            }
        });
    }
}
