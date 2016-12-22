package Pacman.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;

import Pacman.level.Level;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.level.pathfinder.Knoten;
import Pacman.tiles.Tile;

public class Inky extends Ghost{

	public Inky(BufferedImage[] skins, Level level) {
		super(skins, level);
		speed = 5;
	}


	@Override
	public void updateFields() {
		Dijkstra dj = new Dijkstra(level);
		int x = renderX / Tile.TILEWIDTH;
		int y = renderY / Tile.TILEHEIGHT;
		// 2 Felder vor Pacman, Vektor Blinky zu diesem Feld, verdoppeln
		currentDestination = getDestinationNode();
		
		dj.findPath(currentDestination.x, currentDestination.y, x, y);
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

	// TODO returned knoten trifft auf wand oder ist out of range !!! <--------------
	private Point getDestinationNode() {
		Point twoBeforePacman = getXNodesInFrontOfPacman(2); // zwei for Pacman
		Ghost blinky = level.getGhosts()[0]; // Blinky
		Point vektor = new Point(twoBeforePacman.x - blinky.getX(), twoBeforePacman.y - blinky.getY());
		return new Point(vektor.x*2, vektor.y*2);
	}
	
}
