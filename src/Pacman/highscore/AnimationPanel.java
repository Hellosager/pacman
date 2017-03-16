package Pacman.highscore;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import Pacman.gfx.Assets;

public class AnimationPanel extends JPanel{
	private boolean runToRight;
	private Animation[] animations = new Animation[4];
	
	public void init(){
		runToRight = new Random().nextInt(2) == 0 ? true : false;
		animations[0] = new Pacman(runToRight);
		animations[1] = new Ghost(runToRight, Assets.blinky);
		animations[2] = new Ghost(runToRight, Assets.inky);
		animations[3] = new Ghost(runToRight, Assets.pinky);
		newRandomCoords();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Animation a : animations)
			g.drawImage(a.getTexture(), a.getX(), a.getY(), null);
	}
	
	public Animation[] getAnimations(){
		return animations;
	}
	
	public void tick(){
		for(Animation a : animations)
			a.tick();
		if(allOutOfVision()){
			runToRight = new Random().nextInt(2) == 0 ? true : false;
			newRandomCoords();
		}
	}

	// HIER WEITER MACHEN
	public void newRandomCoords(){
		Random r = new Random();
		ArrayList<Integer> coords = new ArrayList();
		if(runToRight){
			for(int x = -120; x < 0; x+=30)
				coords.add(x);
			for(Animation a : animations){
				a.setMoveRight(runToRight);
				int index = r.nextInt(coords.size());
				a.setX(coords.get(index));
				a.setY(getHeight()/2-15);
				coords.remove(index);
			}
		}
			else{
				for(int x = getWidth(); x < getWidth()+120; x+=30)
					coords.add(x);
				for(Animation a : animations){
					a.setMoveRight(runToRight);
					int index = r.nextInt(coords.size());
					a.setX(coords.get(index));
					a.setY(getHeight()/2-15);
					coords.remove(index);
				}
			}
	}
	
	public boolean allOutOfVision(){
		return (animations[0].getX()+25 < -30 && animations[1].getX()+25 < -30 &&
				animations[2].getX()+25 < -30  && animations[3].getX()+25 < -30) ||
				(animations[0].getX() > getWidth()+30 && animations[1].getX() > getWidth()+30 &&
				animations[2].getX() > getWidth()+30  && animations[3].getX() > getWidth()+30);
	}
	
}
