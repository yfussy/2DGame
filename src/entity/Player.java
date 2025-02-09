package entity;

import java.awt.Color;
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
	
	public int hasKey = 0;
	int standCounter = 0;
	boolean moving = false;
	int pixelCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		SCREEN_X = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE/2);
		SCREEN_Y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE/2);
		
		solidArea = new Rectangle();
		solidArea.x = 1;
		solidArea.y = 1;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 46;
		solidArea.height = 46;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.TILE_SIZE * 23;
		worldY = gp.TILE_SIZE * 21;
		speed = 6;
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
		
		if (moving == false) {
			
			if (keyH.upPressed == true || keyH.downPressed == true || 
					keyH.leftPressed == true || keyH.rightPressed == true) {
				
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
				
				moving = true;
				
				// Check Tile Collision
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
				// Check Object Collision
				int objectIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objectIndex);
			} 
			else {
				standCounter++;
				
				if (standCounter == 16) {
					spriteNum = 1;
					standCounter = 0;
				}
			}
		}
		
		if (moving == true) {
			
			// If collision is false, Player can move
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
			if (spriteCounter > 8) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
			
			pixelCounter += speed;
			
			if (pixelCounter >= 46) {
				moving = false;
				pixelCounter = 0;
			}
		}
	}

	
	public void pickUpObject(int i) {
		
		if (i != 999) {
			
			String objectName = gp.obj[i].name;
			switch (objectName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "Door":
				gp.playSE(3);
				if (hasKey > 0) {
					hasKey--;
					gp.obj[i] = null;
					gp.ui.showMessage("You opened the door!");
				} else {
					gp.ui.showMessage("You need a key!");
				}
				break;
			case "Boots":
				gp.playSE(2);
				speed += 2;
				gp.obj[i] = null;
				gp.ui.showMessage("Speed up!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
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
		 
		 // player solid area marker (show hit box)
//		 g2.setColor(Color.red);
//		 g2.drawRect(SCREEN_X + solidArea.x, SCREEN_Y + solidAreaDefaultY, solidArea.width, solidArea.height);
	}
}
