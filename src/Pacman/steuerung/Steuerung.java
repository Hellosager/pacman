package Pacman.steuerung;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

import Pacman.creatures.Creature;
import Pacman.creatures.Player;
import Pacman.game.Game;
import Pacman.gui.MainMenu;

public class Steuerung implements KeyListener{
	private Player player;
	private Game game;
	private Date pausedAt;
	
	public Steuerung(Player player, Game game) {
		this.player = player;
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) && !game.isPaused()){
			if(game.isRunning()){
				player.setNewDirectionSet(true);
				player.setNewDirection(Creature.UP);				
			}
		}
		if((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) && !game.isPaused()){
			if(game.isRunning()){
				player.setNewDirectionSet(true);
				player.setNewDirection(Creature.RIGHT);
			}
		}
		if((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) && !game.isPaused()){
			if(game.isRunning()){
				player.setNewDirectionSet(true);
				player.setNewDirection(Creature.DOWN);				
			}
		}
		if((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) && !game.isPaused()){
			if(game.isRunning()){
				player.setNewDirectionSet(true);
				player.setNewDirection(Creature.LEFT);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_P){
			if(game.isPaused()){
				game.setPaused(false);
				game.setModeTime(new Date(game.getModeTime().getTime() - (new Date().getTime() - pausedAt.getTime())));
			}
			else{
				game.setPaused(true);
				pausedAt = new Date();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_MINUS){
			game.manipulateOuttime(5);
		}
		if(e.getKeyCode() == KeyEvent.VK_PLUS){
			game.manipulateOuttime(-5);
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				game.stop();
				new MainMenu(game.getDisplay());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_X){
			game.getLevel().changeShowDestinations();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
