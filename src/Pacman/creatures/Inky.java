package Pacman.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;

import Pacman.level.Level;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.tiles.Tile;

public class Inky extends Ghost{

	public Inky(BufferedImage[] skins, Level level) {
		super(skins, level);
		speed = 5;
		currentMode = MODE_SPREAD;
	}


	@Override
	public void updateFieldsHunt() {
		Dijkstra dj = new Dijkstra(level);
		// 2 Felder vor Pacman, Vektor Blinky zu diesem Feld, verdoppeln
		currentDestination = getDestinationNode();
		
		dj.findPath(currentDestination.x, currentDestination.y, getX(), getY());
		knotenMap = dj.getTileMapAlsKnoten();			
	}


	@Override
	void updateDirection() {
		// Wenn Feld erreicht wurde, berechne den Weg neu
		if((currentDestination == null) || (currentDestination.x == getX() && currentDestination.y == getY())){
			currentDestination = getXNodesInFrontOfPacman(2);
			updateFields();
		}		
	}

	private Point getDestinationNode() {
		Point twoBeforePacman = getXNodesInFrontOfPacman(2); // zwei for Pacman
		Ghost blinky = level.getGhosts()[0]; // Blinky
		int vektorX = (twoBeforePacman.x - blinky.getX())*2;
		int vektorY = (twoBeforePacman.y - blinky.getY())*2;
		while(!pointIsValid(blinky.getX() + vektorX, blinky.getY() + vektorY)){
			if(Math.abs(vektorX) > Math.abs(vektorY)){	// wenn x koord größer als y ist
				if(vektorX < 0)							// wenn vektorX negativ, dann addieren
					vektorX++;
				else									// sonst subtrahieren
					vektorX--;
			}else{										// Ansonsten ist y größer
				if(vektorY < 0 )						// wenn y negativ, dann addieren +1
					vektorY++;
				else									// Ansonsten -1
					vektorY--;
			}
		}
		// Vektor von Blinky zum legalen doppelten
		return new Point(blinky.getX() + vektorX, blinky.getY() + vektorY);	// das letztendliche Zielfeld
		
	}
	private boolean pointIsValid(int x, int y){
		return	x > 0 && x < level.getWidth() &&	// und auf x in bounds
				y > 0 && y < level.getHeight() &&	// und auf y in bounds
				level.getTileMap()[x][y] != Tile.WALL;	// Vektor valid if no wall
	}
	
	
}
