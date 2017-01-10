package Pacman.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import Pacman.level.Level;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.level.pathfinder.Knoten;
import Pacman.tiles.Tile;

public abstract class Ghost extends Creature{

	protected static final int MODE_HUNT = 1;
	protected static final int MODE_FEAR = 2;
	protected static final int MODE_SPREAD = 3;
	protected static final int MAX_TICK_COUNT = 10;
	
	protected int currentMode;
	private BufferedImage[] skins;
	protected Point currentDestination;
	protected Knoten[][] knotenMap;
	
	public Ghost(BufferedImage[] skins, Level level) {
		super(skins[0], level);
		this.skins = skins;
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
	
	public void updateFields(){
		switch(currentMode){
			case MODE_HUNT: updateFieldsHunt(); break;
			case MODE_SPREAD: updateFieldsSpread(); break;
//			case FEAR:	break;
		}
	}
	
	abstract void updateDirection();
	
	// HUNT - individuell implementieren
	protected abstract void updateFieldsHunt();
	
	// SPREAD MODE
	protected void updateFieldsSpread(){
		Random r = new Random();
		Point[] allWayTiles = level.getAllWayTiles();
		
		Dijkstra dj = new Dijkstra(level);
		int x = getX();
		int y = getY();
		currentDestination = allWayTiles[r.nextInt(allWayTiles.length)];
		
		dj.findPath(currentDestination.x, currentDestination.y, x, y);
		knotenMap = dj.getTileMapAlsKnoten();
	}
	
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
		return Math.abs(vektorX) + Math.abs(vektorY);
	}
	
	// TODO
	public void tick(){
		// Change Skin for every Ghost ok
		changeSkin(skins);
		// Changes direction to the next node
		moveTo(knotenMap[getX()][getY()].getVorgänger());
		// just incrase the the render coords
		move();
		// Muss immer neu implementiert werden
		updateDirection();
	}
	
	
	private void changeSkin(BufferedImage[] skins){
		Random r = new Random();
		if(tickCount == MAX_TICK_COUNT){
			texture = skins[r.nextInt(skins.length)];
			tickCount = 0;
		}else{
			tickCount++;
		}
	}
	
	private void move(){
		switch(direction){	// in welcge richtung bewegen?
		case Creature.UP:	// nach oben
				renderY -= speed;
			break;
		case Creature.RIGHT:	// nach rechts
				renderX += speed;
			break;
		case Creature.DOWN:	// nach unten
				renderY += speed;
			break;
		case Creature.LEFT:	// nach links
				renderX -= speed;
		}
	}
	
	protected Point getXNodesInFrontOfPacman(int fieldsBeforePacman){
		Player p = level.getPlayer();
		int x = p.getX();
		int y = p.getY();
		
		switch(p.getDircetion()){
			case Creature.RIGHT:
				while(level.getTileMap()[x+1][y] != Tile.WALL && x-p.getX() < fieldsBeforePacman)
					x++;
				break;
			case Creature.LEFT:
				while(level.getTileMap()[x-1][y] != Tile.WALL && p.getX()-x < fieldsBeforePacman)
					x--;
				break;
			case Creature.UP:
				while(level.getTileMap()[x][y-1] != Tile.WALL && p.getY()-y < fieldsBeforePacman)
					y--;
				break;
			case Creature.DOWN:
				while(level.getTileMap()[x][y+1] != Tile.WALL && y-p.getY() < fieldsBeforePacman)
					y++;
				break;
		}
		return new Point(x, y);
	}

	
}
