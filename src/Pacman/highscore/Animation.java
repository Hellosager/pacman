package Pacman.highscore;

import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Animation {
	protected int x;
	protected int y;
	protected int tickCount = 0;
	protected int textureIndex = 0;
	protected boolean moveRight = true;
	protected BufferedImage actualTexture;
	
	public Animation(boolean moveRight) {
		this.moveRight = moveRight;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public BufferedImage getTexture(){
		return actualTexture;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y ){
		this.y = y;
	}
	
	public void setMoveRight(boolean moveRight){
		this.moveRight = moveRight;
	}
	
	protected abstract void changeTexture();
	
	// EXCEPTION OUTTA BOUNDS
	protected abstract void tick();
}
