package Pacman.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import Pacman.level.Level;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.level.pathfinder.Knoten;
import Pacman.tiles.Tile;

public abstract class Ghost extends Creature{

	public static final int MODE_HUNT = 1;
	public static final int MODE_FEAR = 2;
	public static final int MODE_SPREAD = 3;
	protected static final int MAX_TICK_COUNT = 10;
	private static final Point[] corners = {new Point(1, 1), new Point(1, 22), new Point(22, 1), new Point(22, 22)};
	
	protected int currentMode;
	private BufferedImage[] skins;
	protected Point currentDestination;
	protected Knoten[][] knotenMap;
	protected int idForFullWayTile;
	protected int idForEmptyWayTile;
	
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
		if(level.showDestinations())
			updateDestination(true);
		switch(currentMode){
			case MODE_HUNT: updateFieldsHunt(); break;
			case MODE_FEAR: updateFieldsFear(); break;
			case MODE_SPREAD: updateFieldsSpread(); break;
		}
		if(level.showDestinations())
			updateDestination(false);
	}
	
	public void updateDestination(boolean clear){
//		if(level.showDestinations()){
			if(!clear){
				level.getTileMap()[currentDestination.x][currentDestination.y] = 
					level.coordIsFullWay(currentDestination.x, currentDestination.y) ? idForFullWayTile : idForEmptyWayTile; 
			}else{
				level.getTileMap()[currentDestination.x][currentDestination.y] = 
						level.coordIsFullWay(currentDestination.x, currentDestination.y) ? Tile.FULL_WAY : Tile.EMPTY_WAY; 
			}
//		}
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
	
	// FEAR MODE
	protected void updateFieldsFear(){
		currentDestination = corners[new Random().nextInt(corners.length)];
		Dijkstra dj = new Dijkstra(level);
		dj.findPath(currentDestination.x, currentDestination.y, this.getX(), this.getY());
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
//		updateDestination(false);
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

	public void changeMode() {
//		updateDestinations();
		if(currentMode == MODE_SPREAD)
			currentMode = 1;
		else
			currentMode++;
	}

	public Point getCurrentDestination(){
		return currentDestination;
	}

	public int getIdForFullWayTile() {
		return idForFullWayTile;
	}

	public int getIdForEmptyWayTile() {
		return idForEmptyWayTile;
	}
	
	public void setCurrentMode(int mode){
		this.currentMode = mode;
	}
	
}
