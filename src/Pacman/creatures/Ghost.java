package Pacman.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;

import Pacman.level.Level;
import Pacman.level.pathfinder.Knoten;
import Pacman.tiles.Tile;

public abstract class Ghost extends Creature{

	protected static final int HUNT = 1;
	protected static final int FEAR = 2;
	protected static final int SPREAD = 3;
	
	protected Point currentDestination;
	protected Knoten[][] knotenMap;
	
	public Ghost(BufferedImage texture, Level level) {
		super(texture, level);
		
	}

	protected void moveTo(Knoten nextKnoten){
		if(renderX % Tile.TILEWIDTH == 0 && renderY % Tile.TILEHEIGHT == 0){
			if(nextKnoten.getX() > getX() && level.getTileMap()[getX()+1][getY()] != Tile.WALL){ // wenn nächster knoten rechts ist
				direction = Creature.RIGHT;
			}else if(nextKnoten.getX() < getX() && level.getTileMap()[(renderX-speed)/Tile.TILEWIDTH][getY()] != Tile.WALL){ // nächster knoten links davon 
				direction = Creature.LEFT;
			}else if(nextKnoten.getY() < getY() && level.getTileMap()[getX()][(renderY-speed)/Tile.TILEHEIGHT] != Tile.WALL ){	// nächster knoten ist drüber
				direction = Creature.UP;
			}else if(nextKnoten.getY() > getY() && level.getTileMap()[getX()][getY()+1] != Tile.WALL){	// nächster knoten ist drunter
					direction = Creature.DOWN;
			}
			else{
				direction = 88;
			}
		}	
	}
	
	public abstract void updateFields();
	
	//switch direction wenn mehr als ein weg oder sackgasse oder altes feld erreicht wurde
	protected boolean canSwitchDirection(){
		int count = countOfNotWallsAround();
		return count >= 3 || count == 1 ||
				(currentDestination.getX() == getX() && currentDestination.getY() == getY());
	}
	
	private int countOfNotWallsAround(){
		int count = 0;
		if(level.getTileMap()[getX()-1][getY()] != Tile.WALL)
			count++;
		if(level.getTileMap()[getX()+1][getY()] != Tile.WALL)
			count++;
		if(level.getTileMap()[getX()][getY()-1] != Tile.WALL)
			count++;
		if(level.getTileMap()[getX()][getY()+1] != Tile.WALL)
			count++;
		
		return count;
	}
	
	protected int getDistanceToPlayer(){
		Player p = level.getPlayer();
		int vektorX = p.getX() - getX();
		int vektorY = p.getY() - getY();
		System.out.println("Abstand zu Player: " +  (Math.abs(vektorX) + Math.abs(vektorY)));
		return Math.abs(vektorX) + Math.abs(vektorY);
	}
	
	// TODO
	public void tick(){
		// Methodenaufruf für Skin
		// moveTo(knotenMap[getX()][getY()].getVorgänger());
		// move();
		// updateDirection();
	}
}
