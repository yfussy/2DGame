package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // terminates on close marker
		window.setResizable(false);
		window.setTitle("Game");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); // causes this window to be sized to fit the preferred size and layouts of its subcomponents
		
		window.setLocationRelativeTo(null); // set location to center
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}
	

}
