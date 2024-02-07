package de.soprasteria.saver;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Starter {

	public static void main(String[] args) {
		System.out.println("Hello World.");
		JFrame frame = new JFrame("Saver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(300,300));
		frame.setVisible(true);
	}

}
