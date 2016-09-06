package Pacman.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Pacman.editor.Editor;
import Pacman.game.Game;
import Pacman.gui.Display;
import Pacman.gui.Levelselect;
import Pacman.gui.MenuButton;
import Pacman.level.Level;

public class MouseInput implements MouseListener{
	
	private MenuButton button;
	private Display display;

	public MouseInput(MenuButton button, Display display) {
		this.button = button;
		this.display = display;
	}
	

	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			switch(button.getName()){
			case "play": 
				new Levelselect(display);
				break;
			case "score": 
				break;
			case "editor": 
				new Editor(display).start();
				break;
			}
			
			
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		button.changeImage();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		button.changeImage();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
