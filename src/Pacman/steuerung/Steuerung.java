package Pacman.steuerung;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Pacman.creatures.Creature;

public class Steuerung implements KeyListener{
	private Creature player;
	
	public Steuerung(Creature player) {
		this.player = player;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			player.setNewDirectionSet(true);
			player.setNewDirection(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			player.setNewDirectionSet(true);
			player.setNewDirection(1);
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			player.setNewDirectionSet(true);
			player.setNewDirection(2);
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			player.setNewDirectionSet(true);
			player.setNewDirection(3);
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
