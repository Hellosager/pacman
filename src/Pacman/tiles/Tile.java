package Pacman.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	public static Tile[] tileTextures = new Tile[10];
	public static Tile wallTile = new WallTile(0);
	public static Tile fullWayTile = new FullWayTile(1);
	public static Tile emptyWayTile = new EmptyWayTile(2);
	public static Tile rasterTile = new RasterTile(3);
	
	public final static int WALL = wallTile.getID(); 
	public final static int FULL_WAY = fullWayTile.getID(); 
	public final static int EMPTY_WAY = emptyWayTile.getID(); 
	
	
	public static final int TILEWIDTH = 25, TILEHEIGHT = 25;
	
	protected BufferedImage texture;
	protected final int id;
	protected boolean solid;
	

	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;

		tileTextures[id] = this;
	}
	
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	protected void setSolid(boolean b){
		solid = b;
	}
	
	public int getID(){
		return id;
	}
	
	public BufferedImage getTexture(){
		return texture;
	}
}
