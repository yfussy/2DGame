package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Key();
		gp.obj[0].worldX = 23 * gp.TILE_SIZE;
		gp.obj[0].worldY = 7 * gp.TILE_SIZE;		
		
		gp.obj[1] = new OBJ_Key();
		gp.obj[1].worldX = 23 * gp.TILE_SIZE;
		gp.obj[1].worldY = 40 * gp.TILE_SIZE;	
		
		gp.obj[2] = new OBJ_Key();
		gp.obj[2].worldX = 38 * gp.TILE_SIZE;
		gp.obj[2].worldY = 8 * gp.TILE_SIZE;
		
		gp.obj[3] = new OBJ_Door();
		gp.obj[3].worldX = 10 * gp.TILE_SIZE;
		gp.obj[3].worldY = 11 * gp.TILE_SIZE;
		
		gp.obj[4] = new OBJ_Door();
		gp.obj[4].worldX = 8 * gp.TILE_SIZE;
		gp.obj[4].worldY = 28 * gp.TILE_SIZE;
		
		gp.obj[5] = new OBJ_Door();
		gp.obj[5].worldX = 12 * gp.TILE_SIZE;
		gp.obj[5].worldY = 22 * gp.TILE_SIZE;
		
		gp.obj[6] = new OBJ_Chest();
		gp.obj[6].worldX = 10 * gp.TILE_SIZE;
		gp.obj[6].worldY = 7 * gp.TILE_SIZE;
	}
}
