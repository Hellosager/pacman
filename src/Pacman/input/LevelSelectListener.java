package Pacman.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;

import Pacman.game.Game;
import Pacman.gfx.ImageLoader;
import Pacman.gui.Levelselect;
import Pacman.gui.MainMenu;
import Pacman.level.Level;

public class LevelSelectListener implements ActionListener{
	private Levelselect ls;

	public LevelSelectListener(Levelselect ls) {
		this.ls = ls;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "select":
				if(ls.getLevelSelector().getSelectedIndex() >= ls.getNormalLevelCount()){
					ls.getLevelPreview().setIcon(new ImageIcon(ImageLoader.loadImage("/images/levelPreviews/nopreview.png")));
				}else{
					String path = "/images/levelPreviews/level" + (ls.getLevelSelector().getSelectedIndex()+1) + "preview.png";
					ls.getLevelPreview().setIcon(new ImageIcon(ImageLoader.loadImage(path)));
				}
				break;
				
			case "menu":
				new MainMenu(ls.getDisplay());
				break;
			
			default:
				if(ls.getLevelSelector().getSelectedIndex() >= ls.getNormalLevelCount()){	// Wenn custom level gew�hlt wird
					Game g = new Game(ls.getDisplay());
					File f = new File("Level");
					if(f.exists()){
						String levelDatei = f.getName() + "/" + f.listFiles()[ls.getLevelSelector().getSelectedIndex()-ls.getNormalLevelCount()].getName();				
						Level level = new Level(levelDatei);
						if(level.isValidToPlay(ls.getDisplay().getFrame())){
							g.setLevel(level);
							g.start();
						}
					}
				}else{	// ansonsten wenn standart level ausgew�hlt wird
					Game g = new Game(ls.getDisplay());
					String levelDatei = "level/level" + (ls.getLevelSelector().getSelectedIndex()+1) + ".txt"; 
					Level level = new Level(levelDatei);
//					if(level.isValidToPlay()){
						g.setLevel(level);
						g.start();
//					}	
				}	
				break;
		}
	}	
}
