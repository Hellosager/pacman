package Pacman.highscore;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Ghost extends Animation{
	private static final int MAX_TICK_COUNT = 20;
	private BufferedImage[] textures; 
	
	public Ghost(boolean moveRight,  BufferedImage[] textures) {
		super(moveRight);
		this.textures = textures;
		actualTexture = textures[0];
	}

	@Override
	protected void changeTexture() {
		Random r = new Random();
		actualTexture = textures[r.nextInt(textures.length)];
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
