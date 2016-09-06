package Pacman.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Pacman.level.Level;
import Pacman.tiles.Tile;

public class Player extends Creature{

	public Player(BufferedImage texture, Level level) {
		super(texture, level);
		speed = 5;
		direction = 1;
	}

	@Override
	public void tick() {
		if(renderX%Tile.TILEWIDTH == 0 && renderY%Tile.TILEHEIGHT == 0 && newDirectionSet ){
			newDirectionSet = false;
			direction = newDirection;
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
			break;
		}
	}
	
	public void render(Graphics g){
		super.render(g);
	}
	
}
