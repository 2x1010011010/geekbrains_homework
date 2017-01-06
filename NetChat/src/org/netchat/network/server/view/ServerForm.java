package org.netchat.network.server.view;

import org.netchat.network.common.history.HistoryWrapper;
import org.netchat.network.server.logic.main.Server;
import org.netchat.network.server.logic.main.ServerException;
import org.netchat.network.server.logic.main.ServerView;
import org.netchat.network.server.logic.main.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerForm extends JFrame implements ServerView {
    private static final String SERVER_ON_TEXT = "SERVER: ON";
    private static final String SERVER_OFF_TEXT = "SERVER: OFF";

    private static final String CONSOLE_SHOW_TEXT = "CONSOLE: SHOW";
    private static final String CONSOLE_HIDE_TEXT = "CONSOLE: HIDE";

    private static final Color SERVER_ON_COLOR = new Color(154, 205, 50);
    private static final Color SERVER_OFF_COLOR = new Color(205, 92, 92);

    public static final Color MESSAGE_COLOR = new Color(255, 165, 0);
    public static final Color INFO_COLOR = new Color(60, 179, 113);
    public static final Color ERROR_COLOR = new Color(178, 34, 34);

    private DefaultTableModel userTableModel;

    private JTable userTable;
    private JButton serverButton;
    private JButton consoleButton;
    private JTextPane consolePane;

    private boolean serverOn;
    private boolean consoleShow;

    private HistoryWrapper historyWrapper;
    private Server server;

    public ServerForm(HistoryWrapper historyWrapper, Server server) throws HeadlessException {
        this.historyWrapper = historyWrapper;
        this.server = server;

        setTitle("Server chat room");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null); //создаст окно на середине
        setLayout(new BorderLayout());

        Color backColor = new Color(224, 224, 224);
        Font font = new Font("Arial", Font.PLAIN, 24);

        this.userTableModel = new DefaultTableModel(new String[]{"ID", "LOGIN"}, 0);
        this.userTable = new JTable(this.userTableModel);
        this.userTable.setBackground(backColor);
        this.userTable.setFont(font);
        JScrollPane userTableScrollPane = new JScrollPane(this.userTable);
        TitledBorder tableBorder = new TitledBorder("Users");
        userTableScrollPane.setBorder(tableBorder);

        this.serverButton = new JButton(SERVER_OFF_TEXT);
        this.serverButton.setBackground(SERVER_OFF_COLOR);
        this.serverButton.setBorderPainted(false);
        this.serverButton.setFocusPainted(false);

        this.consoleButton = new JButton(CONSOLE_HIDE_TEXT);
        this.consoleButton.setBorderPainted(false);
        this.consoleButton.setFocusPainted(false);

        this.consolePane = new JTextPane();
        this.consolePane.setBackground(backColor);
        this.consolePane.setFont(font);
        this.consolePane.setEditable(false);
        JScrollPane consoleScrollPane = new JScrollPane(this.consolePane);
        TitledBorder consoleBorder = new TitledBorder("Console");
        consoleScrollPane.setBorder(consoleBorder);
        consoleScrollPane.setVisible(false);
        consoleScrollPane.setPreferredSize(new Dimension(0, 200));

        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.add(this.consoleButton, BorderLayout.NORTH);
        consolePanel.add(consoleScrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(this.serverButton, BorderLayout.NORTH);
        panel.add(consolePanel, BorderLayout.CENTER);

        add(userTableScrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        JFrame frame = this;

        this.serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (serverOn) {
                    writeInfo("try server turn off");
                    try {
                        if (server != null) {
                            server.turnOff();
                            writeInfo("server turn off");
                        } else {
                            writeInfo("server disabled");
                        }
                    } catch (ServerException se) {
                        se.printStackTrace();
                        writeError(se.getMessage());
                    }
                    serverButton.setText(SERVER_OFF_TEXT);
                    serverButton.setBackground(SERVER_OFF_COLOR);
                    serverOn = false;
                } else {
                    writeInfo("try server turn on");
                    String serverPortText = JOptionPane.showInputDialog(frame, "ENTER SERVER PORT", "Server port", JOptionPane.QUESTION_MESSAGE);
                    if (serverPortText == null || serverPortText.isEmpty()) {
                        writeError(String.format("empty port"));
                        return;
                    }
                    boolean valid = true;
                    for (char c : serverPortText.toCharArray()) {
                        if (!Character.isDigit(c)) {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) {
                        writeError(String.format("wrong port=[%s] example=[9999]", serverPortText));
                        return;
                    }
                    Integer serverPort = Integer.valueOf(serverPortText);
                    writeInfo(String.format("try port=[%s]", serverPort));
                    try {
                        if (server != null) {
                            userTableModel.setRowCount(0);
                            server.setPort(serverPort);
                            server.turnOn();
                            writeInfo("server turn on");
                            serverButton.setText(SERVER_ON_TEXT);
                            serverButton.setBackground(SERVER_ON_COLOR);
                            serverOn = true;
                        } else {
                            writeInfo("server disabled");
                        }
                    } catch (ServerException se) {
                        writeError(se.getMessage());
                        se.printStackTrace();
                    }
                }
            }
        });
        this.consoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (consoleShow) {
                    consoleScrollPane.setVisible(false);
                    consoleButton.setText(CONSOLE_HIDE_TEXT);
                    consoleShow = false;
                } else {
                    consoleScrollPane.setVisible(true);
                    consoleButton.setText(CONSOLE_SHOW_TEXT);
                    consoleShow = true;
                }
            }
        });

        writeInfo("server form ready");
    }

    public void appendText(String msg, Color color) {
        if (msg == null || msg.isEmpty() || color == null)
            return;
        this.consolePane.setEditable(true);
        StyleContext context = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        int len = this.consolePane.getDocument().getLength();
        this.consolePane.setCaretPosition(len);
        this.consolePane.setCharacterAttributes(attributeSet, false);
        this.consolePane.replaceSelection(msg);
        this.consolePane.setEditable(false);
    }

    private void writeInfo(String s) {
        if (s == null || s.isEmpty())
            return;
        String text = String.format("INFO: %s\n", s);
        appendText(text, INFO_COLOR);
        writeHisory(text);
    }

    @Override
    public void writeError(String s) {
        if (s == null || s.isEmpty())
            return;
        String text = String.format("ERROR: %s\n", s);
        appendText(text, ERROR_COLOR);
        writeHisory(s);
    }

    public void writeMessage(String s) {
        if (s == null || s.isEmpty())
            return;
        String text = String.format("MESSAGE: %s\n", s);
        appendText(text, MESSAGE_COLOR);
        writeHisory(s);
    }

    private void writeHisory(String s) {
        if (this.historyWrapper == null || s == null || s.isEmpty())
            return;
        this.historyWrapper.write(s);
    }

    @Override
    public void addUser(User user) {
        if (user == null) {
            return;
        }
        writeInfo(String.format("Add new user=[%s]", user.getLogin()));
        this.userTableModel.addRow(new Object[]{String.valueOf(user.getId()), user.getLogin()});
    }

    @Override
    public void removeUser(User user) {
        if (user == null) {
            return;
        }
        writeInfo(String.format("Remove user=[%s]", user.getLogin()));
        String userId = String.valueOf(user.getId());
        for (int i = 0; i < this.userTableModel.getRowCount(); i++) {
            if (userId.equals(this.userTableModel.getValueAt(i, 0))) {
                this.userTableModel.removeRow(i);
                break;
            }
        }
    }
}
