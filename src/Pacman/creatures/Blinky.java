package Pacman.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;

import Pacman.level.Level;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.tiles.Tile;

public class Blinky extends Ghost{
	// nach Ghost refactoren
	protected final static int MAX_HANDICAP_COUNTER = 10;
	
	private final static int MIN_RANGE_TO_PLAYER_TO_STAY_CHILLED = 4;
	private int handicapCounter = 0;
	
	
	public Blinky(BufferedImage[] skins, Level level) {
		super(skins, level);
		speed = 5;
		currentMode = MODE_SPREAD;
	}
	
	public void updateFieldsHunt(){
		Dijkstra dj = new Dijkstra(level);
		int x = renderX / Tile.TILEWIDTH;
		int y = renderY / Tile.TILEHEIGHT;
		dj.findPath(level.getPlayer().getX(), level.getPlayer().getY(), x, y);
		
		knotenMap = dj.getTileMapAlsKnoten();
	}


	@Override
	void updateDirection() {
		if(canSwitchDirection()){
			// wenn Distance unter der Minimalgrenze ist, dann wird jeder tick neu gesetzt
			if(getDistanceToPlayer() < MIN_RANGE_TO_PLAYER_TO_STAY_CHILLED){
				currentDestination = new Point(level.getPlayer().getX(), level.getPlayer().getY());
				updateFields();							
			// Ansonsten nur alle paar ticks, enn der handicap counter größer als max handicap ist
			}else if(handicapCounter >= MAX_HANDICAP_COUNTER){
				currentDestination = new Point(level.getPlayer().getX(), level.getPlayer().getY());
				updateFields();
				handicapCounter = 0;
			}
			handicapCounter++;
		}		
	}
	
	
}
