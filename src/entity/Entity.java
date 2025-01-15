package entity;

import java.awt.image.BufferedImage;

public class Entity {
	
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // BufferedImage describes an image with an accessible buffer of image data (ways to store image files)
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
}
