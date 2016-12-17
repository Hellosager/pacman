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
		Point fourBeforePac = getFourNodesInFrontOfPacman();
		
		dj.findPath(fourBeforePac.x, fourBeforePac.y, x, y);
		knotenMap = dj.getTileMapAlsKnoten();		
	}

	@Override
	void updateDirection() {
		if((currentDestination == null) || (currentDestination.x == getX() && currentDestination.y == getY())){
			currentDestination = getFourNodesInFrontOfPacman();
			updateFields();
		}
	}
	
	private Point getFourNodesInFrontOfPacman(){
//		if(getX() == currentDestination.x && getY() == currentDestination.y){
			
		Player p = level.getPlayer();
		int x = p.getX();
		int y = p.getY();
		
		switch(p.getDircetion()){
			case Creature.RIGHT:
				while(level.getTileMap()[x+1][y] != Tile.WALL && x-p.getX() < 4)
					x++;
				break;
			case Creature.LEFT:
				while(level.getTileMap()[x-1][y] != Tile.WALL && p.getX()-x < 4)
					x--;
				break;
			case Creature.UP:
				while(level.getTileMap()[x][y-1] != Tile.WALL && p.getY()-y < 4)
					y--;
				break;
			case Creature.DOWN:
				while(level.getTileMap()[x][y+1] != Tile.WALL && y-p.getY() < 4)
					y++;
				break;
		}
		
		return new Point(x, y);
//		}
//		return currentDestination;
	}

}
