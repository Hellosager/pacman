package Pacman.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Pacman.level.Level;
import Pacman.tiles.Tile;

public abstract class Creature {
	private static final int HITBOX_SIZE = 15;
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	
	protected BufferedImage texture;
	protected int renderX, renderY;
	protected int spawnX, spawnY;
	protected int speed;
	protected int direction;
	protected int newDirection;
	protected Level level;
	protected boolean newDirectionSet = false;
	protected int tickCount = 0;
	
	protected Rectangle hitbox = new Rectangle(HITBOX_SIZE, HITBOX_SIZE);
	
	public Creature(BufferedImage texture, Level level){
		this.texture = texture;
		this.level = level;
	}
	
	public void render(Graphics g) {
		g.drawImage(texture, renderX, renderY, null);
	}
	
	public abstract void tick();
	
	/*
	 * this.x = x*Tile.Tilewidht: führt dazu, dass pacman sich bewegt
	 * this.x =x: führt dazu, dass wände im editor nicht auf spawns platziert werden können
	 */
	public void setSpawnX(int x){
		this.spawnX = x;
		this.renderX = x*Tile.TILEWIDTH;
	}
	// TODO
	public void setSpawnY(int y){
		this.spawnY = y;
		this.renderY = y*Tile.TILEHEIGHT;
	}

	public int getRenderX(){
		return renderX;
	}
	
	public int getRenderY(){
		return renderY;
	}
	
	public int getSpawnX(){
		return spawnX;
	}
	
	public int getSpawnY(){
		return spawnY;
	}
	
	public boolean isRenderable(){
		if(renderX != 0 && renderY != 0)
			return true;
		return false;
	}
	
	public void setNewDirection(int newDirection){
		this.newDirection = newDirection;
	}
	
	public void setNewDirectionSet(boolean b){
		newDirectionSet = b;
	}
	
	public void setTexture(BufferedImage newTexture){
		this.texture = newTexture;
	}
	
	public int getDircetion(){
		return direction;
	}
	
	public int getX(){
		return renderX / Tile.TILEWIDTH;
	}
	
	public int getY(){
		return renderY / Tile.TILEHEIGHT;
	}
	
	public Rectangle getHitbox(){
		hitbox.setLocation(renderX+5, renderY+5);
		return hitbox;
	}
}
