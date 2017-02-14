package Pacman.game;

import Pacman.highscore.Highscore;
import Pacman.level.Level;

public class GameOverThread implements Runnable{

	private Game game;
	private Level level;
	
	public GameOverThread(Game game, Level level){
		this.game = game;
		this.level = level;
	}
	
	public void run() {
		new Runnable() {
			public void run() {
				if(!game.escapeHasBeenPressed())
				for(int row = 0; row < level.getHeight(); row++){
					if(!game.escapeHasBeenPressed())
					for(int col = 0; col < level.getWidth(); col++){
						level.getTileMap()[col][row] = 2;
					}
					if(!game.escapeHasBeenPressed())
					game.render();
					try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
				}
				if(!game.escapeHasBeenPressed())
				game.render();
			}
		}.run();
		
		if(!game.escapeHasBeenPressed())
			new Highscore(game.getDisplay(), game);
	}


}
