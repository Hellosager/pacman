package Pacman.creatures;

import java.awt.image.BufferedImage;
import java.util.Random;

import Pacman.gfx.Assets;
import Pacman.level.Level;
import Pacman.tiles.Tile;

public class Blinky extends Creature{
	private Random r = new Random();
	private int counter = 0;
	
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
		
		// Updatelogik
		if(renderX%Tile.TILEWIDTH == 0 && renderY%Tile.TILEHEIGHT == 0 && newDirectionSet ){
			newDirectionSet = false;
		}
		switch(direction){
		case 0:	// nach oben
			if(level.getTileMap()[renderX/Tile.TILEWIDTH][(renderY-speed)/Tile.TILEHEIGHT] != 0 ){	// wenn der drüber keine Wall
				renderY -= speed;
			}
			break;
		case 1:	// nach rechts
			if(level.getTileMap()[(renderX/Tile.TILEWIDTH)+1][renderY/Tile.TILEHEIGHT] != 0){
				renderX += speed;
			}
			break;
		case 2:	// nach unten
			if(level.getTileMap()[renderX/Tile.TILEWIDTH][renderY/Tile.TILEHEIGHT+1] != 0){
				renderY += speed;
			}
			break;
		case 3:	// nach links
			if(level.getTileMap()[(renderX-speed)/Tile.TILEWIDTH][renderY/Tile.TILEHEIGHT] != 0){
				renderX -= speed;
			}
			default:
				
			break;
		}
		if(renderX%Tile.TILEWIDTH == 0 && renderY%Tile.TILEHEIGHT == 0){
			counter++;
		}
		if(counter == 3){
			counter = 0;
			newDirectionSet = true;
			int preDirection = direction;
			while(direction == preDirection)
				direction = r.nextInt(4);
		}
	}
}
