package Pacman.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	public static Tile[] tileTextures = new Tile[10];
	public static Tile wallTile = new WallTile(0);
	public static Tile fullWayTile = new FullWayTile(1);
	public static Tile emptyWayTile = new EmptyWayTile(2);
	public static Tile rasterTile = new RasterTile(3);
	public static Tile fullWayTileRed = new FullWayTileRed(4);
	public static Tile fullWayTileBlue = new FullWayTileBlue(5);
	public static Tile fullWayTilePink = new FullWayTilePink(6);
	public static Tile emptyWayTileRed = new EmptyWayTileRed(7);
	public static Tile emptyWayTileBlue = new EmptyWayTileBlue(8);
	public static Tile emptyWayTilePink = new EmptyWayTilePink(9);
	
	public final static int WALL = wallTile.getID();
	public final static int FULL_WAY = fullWayTile.getID();
	public final static int EMPTY_WAY = emptyWayTile.getID();
	public final static int EMPTY_WAY_RED = emptyWayTileRed.getID();
	public final static int EMPTY_WAY_BLUE = emptyWayTileBlue.getID();
	public final static int EMPTY_WAY_PINK = emptyWayTilePink.getID();
	public final static int FULL_WAY_RED = fullWayTileRed.getID();
	public final static int FULL_WAY_BLUE = fullWayTileBlue.getID();
	public final static int FULL_WAY_PINK = fullWayTilePink.getID();
	
	
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
}
