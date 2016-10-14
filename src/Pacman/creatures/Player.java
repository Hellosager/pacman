package Pacman.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Pacman.gfx.Assets;
import Pacman.level.Level;
import Pacman.tiles.Tile;

public class Player extends Creature{
	private int state = 0;

	public Player(BufferedImage texture, Level level) {
		super(texture, level);
		speed = 5;
		direction = 1;
		maxTickCount = 1;
	}

	@Override
	public void tick() {
		if(renderX%Tile.TILEWIDTH == 0 && renderY%Tile.TILEHEIGHT == 0 && newDirectionSet ){
			newDirectionSet = false;
			direction = newDirection;
		}
		
		texture = Assets.pacmanDircetions[direction][Math.abs(state)];
		
		// kann mi Math.abs geregelt werden siehe eins oben, state soll nur alle paar frames geregelt werden 
		if(tickCount == maxTickCount){
			if(state < Assets.pacmanDircetions[direction].length-1)
				state++;
			else
				state = -(Assets.pacmanDircetions[direction].length-1);
		tickCount = 0;
		}else{
			tickCount++;
		}
		
		switch(direction){
		case UP:	// nach oben
			if(level.getTileMap()[renderX/Tile.TILEWIDTH][(renderY-speed)/Tile.TILEHEIGHT] != 0 ){	// wenn der drüber keine Wall
				renderY -= speed;
			}
			break;
		case RIGHT:	// nach rechts
			if(level.getTileMap()[(renderX/Tile.TILEWIDTH)+1][renderY/Tile.TILEHEIGHT] != 0){
				renderX += speed;
			}
			break;
		case DOWN:	// nach unten
			if(level.getTileMap()[renderX/Tile.TILEWIDTH][renderY/Tile.TILEHEIGHT+1] != 0){
				renderY += speed;
			}
			break;
		case LEFT:	// nach links
			if(level.getTileMap()[(renderX-speed)/Tile.TILEWIDTH][renderY/Tile.TILEHEIGHT] != 0){
				renderX -= speed;
			}
			break;
		}
	}	
}
