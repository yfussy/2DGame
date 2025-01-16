package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int SCREEN_X;
	public final int SCREEN_Y;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		SCREEN_X = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE/2);
		SCREEN_Y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.TILE_SIZE * 23;
		worldY = gp.TILE_SIZE * 21;
		speed = 10;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if (keyH.upPressed == true) {
				direction = "up";
			} 
			else if (keyH.downPressed == true) {
				direction = "down";
			} 
			else if (keyH.leftPressed == true) {
				direction = "left";
			} 
			else if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			// Check Tile Collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			if (collisionOn == false) {
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			// Change image every 10 frames
			spriteCounter++;
			if (spriteCounter > 4) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		 BufferedImage image = null;
		 
		 switch (direction) {
		 case "up":
			 if (spriteNum == 1) {				 
				 image = up1;
			 }
			 if (spriteNum == 2) {
				 image = up2;
			 }
			 break;
		 case "down":
			 if (spriteNum == 1) {				 
				 image = down1;
			 }
			 if (spriteNum == 2) {
				 image = down2;
			 }
			 break;
		 case "left":
			 if (spriteNum == 1) {
				 image = left1;
			 }
			 if (spriteNum == 2) {
				 image = left2;
			 }
			 break;
		 case "right":
			 if (spriteNum == 1) { 
				 image = right1;
			 }
			 if (spriteNum == 2) {
				 image = right2;
			 }
			 break;
		 }
		 g2.drawImage(image, SCREEN_X, SCREEN_Y, gp.TILE_SIZE, gp.TILE_SIZE, null); // Draw image on screen
	}
}
