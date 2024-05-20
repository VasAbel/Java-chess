package Sakk;

import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main implements Runnable {
	public void run() {
		SwingUtilities.invokeLater(new Menu());
	}

	public static void main(String[] args) throws IOException {
		
		SwingUtilities.invokeLater(new Main());

	}
}
