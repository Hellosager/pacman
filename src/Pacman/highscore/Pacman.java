package Pacman.highscore;

import java.awt.image.BufferedImage;

import Pacman.creatures.Creature;
import Pacman.gfx.Assets;

public class Pacman extends Animation{
	private static final int MAX_TICK_COUNT = 1;
	private BufferedImage[] rightMoves;
	private BufferedImage[] leftMoves;

	public Pacman(boolean moveRight) {
		super(moveRight);
		rightMoves = Assets.pacmanDirections[Creature.RIGHT];
		leftMoves = Assets.pacmanDirections[Creature.LEFT];
		actualTexture = moveRight ? rightMoves[0] : leftMoves[0];
	}

	@Override
	protected void changeTexture() {
		if(textureIndex < rightMoves.length-1)
			textureIndex++;
		else
			textureIndex = -(rightMoves.length-1);
		actualTexture = moveRight ? rightMoves[Math.abs(textureIndex)] : leftMoves[Math.abs(textureIndex)];
	}

	@Override
	protected void tick() {
		if(moveRight){
			x++;
		}else{
			x--;
		}
		
		if(tickCount == MAX_TICK_COUNT){
			changeTexture();
			tickCount = 0;
		}else{
			tickCount++;
		}
	}

}
