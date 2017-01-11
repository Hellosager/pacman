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
		if(e.getKeyCode() == KeyEvent.VK_W && !game.isPaused()){
			player.setNewDirectionSet(true);
			player.setNewDirection(Creature.UP);
		}
		if(e.getKeyCode() == KeyEvent.VK_D && !game.isPaused()){
			player.setNewDirectionSet(true);
			player.setNewDirection(Creature.RIGHT);
		}
		if(e.getKeyCode() == KeyEvent.VK_S && !game.isPaused()){
			player.setNewDirectionSet(true);
			player.setNewDirection(Creature.DOWN);
		}
		if(e.getKeyCode() == KeyEvent.VK_A && !game.isPaused()){
			player.setNewDirectionSet(true);
			player.setNewDirection(Creature.LEFT);
		}
		if(e.getKeyCode() == KeyEvent.VK_P){
			if(game.isPaused()){
				game.setPaused(false);
				game.setModeTime(new Date(game.getModeTime().getTime() + pausedAt.getTime()));
			}
			else{
				game.setPaused(true);
				pausedAt = new Date();
			}
			
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				game.stop();
				new MainMenu(game.getDisplay());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
