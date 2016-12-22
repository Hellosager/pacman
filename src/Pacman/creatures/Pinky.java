package Pacman.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;

import Pacman.level.Level;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.tiles.Tile;

public class Pinky extends Ghost{

	public Pinky(BufferedImage[] skins, Level level) {
		super(skins, level);
		speed = 5;
	}

	@Override
	public void updateFields() {
		Dijkstra dj = new Dijkstra(level);
		int x = renderX / Tile.TILEWIDTH;
		int y = renderY / Tile.TILEHEIGHT;
		// 4 Felder vor Pacman berechnen
		currentDestination = getXNodesInFrontOfPacman(4);
		
		dj.findPath(currentDestination.x, currentDestination.y, x, y);
		knotenMap = dj.getTileMapAlsKnoten();		
	}

	@Override
	void updateDirection() {
		// Wenn das Feld erreicht wurde, berechne den Weg neu
		if((currentDestination == null) || (currentDestination.x == getX() && currentDestination.y == getY())){
			currentDestination = getXNodesInFrontOfPacman(4);
			updateFields();
		}
	}
}
