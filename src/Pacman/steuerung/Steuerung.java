package Pacman.steuerung;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Pacman.creatures.Creature;
import Pacman.creatures.Player;
import Pacman.game.Game;

public class Steuerung implements KeyListener{
	private Player player;
	private Game game;
	
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
			game.setPaused(game.isPaused() ? false : true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
