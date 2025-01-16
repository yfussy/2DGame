package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int ORIGINAL_TILE_SIZE = 16; // 16x16 (px) tiles
	final int SCALE = 3; // scale up
	
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
	public final int MAX_SCREEN_COL = 16;
	public final int MAX_SCREEN_ROW = 12;
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768px
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576px
	
	// WORLD SETTINGS
	public final int MAX_WORLD_COL = 50;
	public final int MAX_WORLD_ROW = 50;
	
	// FPS
	final int FPS = 60;
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true); // focus on this window -> keyboard input received
	}
	
	public void setUpGame() {
		
		aSetter.setObject();
		
		playMusic(0);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start(); // call run method
	}
	
	@Override // Runnable interface
	public void run() { // another game loop method (more preferred)
		
		// Limit game loop to FPS
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		// Show FPS
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime); 
			lastTime = currentTime;
			
			if (delta >= 1) { // start update when time meets condition
				// 1 UPDATE: update information such as character positions
				update();
				// 2 DRAW: draw the screen with the updated information
				repaint(); // call "paintComponent" method		
				delta--;
				drawCount++;
			}
			
//			if (timer >= 1000000000) {
//				System.out.println("FPS:"+drawCount);
//				timer = 0;
//				drawCount = 0;
//			}
		}
	}

	public void runSleep() { // run this method automatically when Thread is being called
		
		double drawInterval = 1000000000/FPS; // 1 billion nanosecond -> 1 second
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) { // loop the game
			
			update();
			repaint();
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime /= 1000000; // sleep accepts millisecond
				
				if (remainingTime < 0) {
					remainingTime = 0; // if loop takes longer than drawInterval -> no sleep
				}
				
				Thread.sleep((long) remainingTime); // pause the game loop
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void update() {
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g); // super -> JPanel
		
		Graphics2D g2 = (Graphics2D) g;
		
		// Draw order states layer
		
		// TILES
		tileM.draw(g2);
		
		// OBJECTS
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		// PLAYERS
		player.draw(g2);
		
		
		g2.dispose(); // dispose the graphic context and release any system resources that is used (save memory)
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
}
