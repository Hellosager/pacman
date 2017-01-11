package Pacman.creatures;

import java.awt.image.BufferedImage;

import Pacman.level.Level;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.tiles.Tile;

public class Pinky extends Ghost{

	public Pinky(BufferedImage[] skins, Level level) {
		super(skins, level);
		speed = 5;
		currentMode = MODE_SPREAD;
	}

	@Override
	public void updateFieldsHunt() {
		Dijkstra dj = new Dijkstra(level);
		// 4 Felder vor Pacman berechnen
		currentDestination = getXNodesInFrontOfPacman(4);
		
		dj.findPath(currentDestination.x, currentDestination.y, getX(), getY());
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
