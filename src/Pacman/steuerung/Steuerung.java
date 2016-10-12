package Pacman.steuerung;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Pacman.creatures.Creature;
import Pacman.creatures.Player;
import Pacman.gfx.Assets;

public class Steuerung implements KeyListener{
	private Player player;
	
	public Steuerung(Player player) {
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			player.setNewDirectionSet(true);
			player.setNewDirection(Creature.UP);
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			player.setNewDirectionSet(true);
			player.setNewDirection(Creature.RIGHT);
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			player.setNewDirectionSet(true);
			player.setNewDirection(Creature.DOWN);
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			player.setNewDirectionSet(true);
			player.setNewDirection(Creature.LEFT);
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
