package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.MAX_SCREEN_COL][gp.MAX_SCREEN_ROW];
		
		getTileImage();
		loadMap();
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap() {
		
		try {
			
			InputStream is = getClass().getResourceAsStream("/maps/map01.txt"); // import textfile
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // read content in textfile
			
			int col = 0;
			int row = 0;
			
			while(col < gp.MAX_SCREEN_COL && row < gp.MAX_SCREEN_ROW) {
				
				String line = br.readLine();
				String numbers[] = line.split(" "); // split and put each element to list
				
				while (col < gp.MAX_SCREEN_COL) {
					
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
					
				}
				if (col == gp.MAX_SCREEN_COL) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch (Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while (col < gp.MAX_SCREEN_COL && row < gp.MAX_SCREEN_ROW) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
			col++;
			x += gp.TILE_SIZE;
			
			if (col == gp.MAX_SCREEN_COL) {
				System.out.println("yeye");
				col = 0;
				x = 0;
				row++;
				y += gp.TILE_SIZE;
			}
		}
	}
}
