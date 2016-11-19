package org.anisimov.gbjava2;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ChatGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public ChatGUI() throws HeadlessException {
		 setTitle("-=Anonimous_Chat=-");
	     setDefaultCloseOperation(EXIT_ON_CLOSE);
	     setSize(620, 480);
	     setLocationRelativeTo(null);
	     setResizable(true);
	     JPanel jPanel = new JPanel();
	     jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
	     
//Main chat panel====================================================================
	     JPanel jPanel1 = new JPanel(new BorderLayout());
	     JTextArea jTextArea = new JTextArea();
	     jTextArea.setEditable(false);
	     JScrollPane jScroll = new JScrollPane(jTextArea);
	     jPanel1.add(jScroll, BorderLayout.CENTER);
	     
//Message panel======================================================================
	     JPanel jPanel2 = new JPanel();
	     jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.X_AXIS));
	     JTextField jTextField = new JTextField();
	     JLabel jLabel = new JLabel("Value:");
	     jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	     //JScrollPane jScrollTextField = new JScrollPane(jTextField);
	     JSlider jSlider = new JSlider(0, 100);
	     jSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
	     jSlider.setValue(50);
	     jSlider.addChangeListener(new ChangeListener() {
	         @Override
	         public void stateChanged(final ChangeEvent e) {
	             jLabel.setText(String.format("Value: %s", jSlider.getValue()));
	         }
	     });
	     jTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
	     jTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 27));
	     jTextField.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(final ActionEvent e) {
	             String s = jTextField.getText();
	             if (s == null || s.length() == 0)
	                 return;
	             for (char c : s.toCharArray()) {
	                 if (!Character.isDigit(c)) {
	                     return;
	                 }
	             }
	         jSlider.setValue(Integer.valueOf(s));
	         }
	     });
	     JButton jButtonSend = new JButton("Send Message");
	     jPanel2.add(jTextField);
	     jPanel2.add(jButtonSend);

//Main menu bar==================================================================================
	     JMenuBar jMenuBar = new JMenuBar();
	     JMenu jMenuChat = new JMenu("Chat");
	     
	     JMenuItem jMenuItemNewRoom = new JMenuItem("Create new room");
	     JMenuItem jMenuItemInvite = new JMenuItem("Invite in the room");
	     JMenuItem jMenuItemEnterRoom = new JMenuItem("Enter in the room");
	     JMenuItem jMenuItemLeaveRoom = new JMenuItem("Leave room");
	     JMenuItem jMenuItemExitChat = new JMenuItem("Exit");
	     
	     JMenu jMenuSet = new JMenu("Settings");
	     JMenuItem jMenuItemNickname = new JMenuItem("Enter nickname");
	     JMenuItem jMenuItemPreferences = new JMenuItem("Preferences");
	     
	     JMenu jMenuHelp = new JMenu("Help");
	     JMenuItem jMenuItemHelp = new JMenuItem("Help");
	     JMenuItem jMenuItemAbout = new JMenuItem("About chat");
	     
	     
	     jMenuBar.add(jMenuChat);
	     jMenuChat.add(jMenuItemNewRoom);
	     jMenuChat.add(jMenuItemInvite);
	     jMenuChat.add(jMenuItemEnterRoom);
	     jMenuChat.add(jMenuItemLeaveRoom);
	     jMenuChat.addSeparator();
	     jMenuChat.add(jMenuItemExitChat);
	     jMenuItemExitChat.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(final ActionEvent e) {
	                System.exit(0);
	            }
	        });
	     
	     jMenuBar.add(jMenuSet);
	     jMenuSet.add(jMenuItemNickname);
	     jMenuSet.add(jMenuItemPreferences);
	     
	     jMenuBar.add(jMenuHelp);
	     jMenuHelp.add(jMenuItemHelp);
	     jMenuHelp.add(jMenuItemAbout);
	     
	     add(jPanel);
	     jPanel.add(jPanel1);
	     jPanel.add(jPanel2);
	     setJMenuBar(jMenuBar);
	     
	}
}
