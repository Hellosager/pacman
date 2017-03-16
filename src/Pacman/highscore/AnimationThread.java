package Pacman.highscore;

public class AnimationThread extends Thread{
	boolean running = true;
	Animation[] animations;
	AnimationPanel ap;
	
	public AnimationThread(AnimationPanel ap) {
		this.ap = ap;
		this.animations = ap.getAnimations();
	}
	
	public void run() {
		while(running){
			ap.tick();
			ap.repaint();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
}
