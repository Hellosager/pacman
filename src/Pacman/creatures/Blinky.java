package Pacman.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import Pacman.gfx.Assets;
import Pacman.level.Level;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.tiles.Tile;

public class Blinky extends Ghost{
	private Random r = new Random();
	
	public Blinky(BufferedImage texture, Level level) {
		super(texture, level);
		speed = 5;
		direction = r.nextInt(4);
		maxTickCount = 10;
	}

	
	@Override
	public void tick() {

		// fuer den Skin
		if(tickCount == maxTickCount){
			texture = Assets.blinky[r.nextInt(5)];
			tickCount = 0;
		}else{
			tickCount++;
		}
		
		moveTo(knotenMap[getX()][getY()].getVorgänger());

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
		

		if(canSwitchDirection()){
			currentDestination = new Point(level.getPlayer().getX(), level.getPlayer().getY());
			updateFields();			
		}
	}
	
	public void updateFields(){
		Dijkstra dj = new Dijkstra(level);
		int x = renderX / Tile.TILEWIDTH;
		int y = renderY / Tile.TILEHEIGHT;
		dj.findPath(level.getPlayer().getX(), level.getPlayer().getY(), x, y);
		
		knotenMap = dj.getTileMapAlsKnoten();
	}
}
