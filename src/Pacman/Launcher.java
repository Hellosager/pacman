package Pacman;

import Pacman.gui.Display;

public class Launcher {

	public static void main(String[] args){
		Utils.createReadme();
		new Display("Pacman");
	}
	
}
