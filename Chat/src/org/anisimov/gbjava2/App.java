package org.anisimov.gbjava2;

import javax.swing.*;

public class App {

	public static void main(String[] args) {
		String window = "-=Anonimous Chat=-";
		String nickName = "Guest#";
		JFrame frame = new ChatGUI(window, nickName);
		frame.setVisible(true);
	}

}
