package org.netchat.network.client.view;
import org.netchat.network.client.logic.main.Client;
import org.netchat.network.client.logic.main.ClientException;
import org.netchat.network.client.logic.main.ClientView;
import org.netchat.network.common.history.HistoryWrapper;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ChatClientForm extends JFrame implements ClientView {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Color SEND_MSG = new Color(255, 165, 0);
    public static final Color RECEIVE_MSG = new Color(60, 179, 113);
    public static final Color ERROR_MSG = new Color(178, 34, 34);

    private static final String PLACEHOLDER = "Write message...";

    private JTextPane textPane;
    private JTextField textField;
    private JButton sendButton;

    private HistoryWrapper historyWrapper;

    private JFrame loginFrame;
    private Client client;

    public ChatClientForm(HistoryWrapper historyWrapper, Client client) throws HeadlessException {
        this.historyWrapper = historyWrapper;
        this.client = client;

        setTitle("Network chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(350, 400);
        setLocationRelativeTo(null); //создаст окно на середине
        setLayout(new BorderLayout());

        Font font = new Font("Arial", Font.PLAIN, 24);
        Color backColor = new Color(224, 224, 224);

        this.textPane = new JTextPane();
        this.textPane.setFont(font);
        this.textPane.setBackground(backColor);
        this.textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(this.textPane);

        this.textField = new JTextField(PLACEHOLDER);
        this.textField.setFont(font);
        this.textField.setBackground(backColor);

        this.sendButton = new JButton("Send");
        this.sendButton.setFocusable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(this.textField, BorderLayout.CENTER);
        panel.add(this.sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        this.textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                textField.setText(PLACEHOLDER);
            }

            @Override
            public void focusGained(final FocusEvent e) {
                textField.setText("");
            }
        });
        this.textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String text = textField.getText();
                sendMessage(text);
                textField.setText("");
            }
        });
        this.sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String text = textField.getText();
                if (!PLACEHOLDER.equals(text)) {
                    sendMessage(text);
                }
            }
        });
    }

    public void sendMessage(String msg) {
        if (msg != null && !msg.isEmpty()) {
            if (this.client != null) {
                Color color = SEND_MSG;
                String text = String.format("me:%s\n", msg);
                try {
                    this.client.sendMessage(msg);
                } catch (ClientException e) {
                    e.printStackTrace();
                    text = String.format("me:%s Error=%s\n", msg, e.getMessage());
                    color = ERROR_MSG;
                }
                appendText(text, color);
            }
        }
        textField.setText("");
    }

    public void appendText(String msg, Color color) {
        if (msg == null || msg.isEmpty() || color == null)
            return;
        this.textPane.setEditable(true);
        StyleContext context = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        int len = this.textPane.getDocument().getLength();
        this.textPane.setCaretPosition(len);
        this.textPane.setCharacterAttributes(attributeSet, false);
        this.textPane.replaceSelection(msg);
        this.textPane.setEditable(false);

        if (this.historyWrapper != null) {
            this.historyWrapper.write(msg);
        }
    }

    public void receive(String msg) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        String text = String.format("%s\n", msg);
        appendText(text, RECEIVE_MSG);
    }

    public JFrame getLoginFrame() {
        return this.loginFrame;
    }

    public void setLoginFrame(JFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    @Override
    public void writeMessage(String message) {
        appendText(String.format("%s\n", message), RECEIVE_MSG);
    }

    @Override
    public void writeError(String error) {
        appendText(String.format("%s\n", error), ERROR_MSG);
    }

    @Override
    public void logout() {
        if (this.loginFrame != null) {
            this.setVisible(false);
            this.loginFrame.setVisible(true);
        }
    }
}
