package Pacman.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import Pacman.game.Game;
import Pacman.gui.Levelselect;
import Pacman.gui.MainMenu;
import Pacman.level.Level;

public class LevelSelectListener implements ActionListener{
	private Levelselect ls;
	private Level previewLevel;

	public LevelSelectListener(Levelselect ls) {
		this.ls = ls;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "select":
				if(ls.getLevelSelector().getSelectedIndex() >= ls.getNormalLevelCount()){	// Wenn custom level ausgewählt
					File f = new File("Level");
					if(f.exists()){
						String levelDatei = f.getName() + "/" + f.listFiles()[ls.getLevelSelector().getSelectedIndex()-ls.getNormalLevelCount()].getName();				
						previewLevel = new Level(levelDatei);
					}
//					ls.getLevelPreview().setIcon(new ImageIcon(ImageLoader.loadImage("/images/levelPreviews/nopreview.png")));
				}else{	// wenn normal level augewählt
					String levelDatei = "level/level" + (ls.getLevelSelector().getSelectedIndex()+1) + ".txt"; 
					previewLevel = new Level(levelDatei);
//					String path = "/images/levelPreviews/level" + (ls.getLevelSelector().getSelectedIndex()+1) + "preview.png";
//					ls.getLevelPreview().setIcon(new ImageIcon(ImageLoader.loadImage(path)));
				}
				ls.getPreviewCanvas().setCurrentRenderLevel(previewLevel);
				ls.getPreviewCanvas().repaint();
				break;
				
			case "menu":
				new MainMenu(ls.getDisplay());
				break;
			
			default:	// Play
				if(ls.getLevelSelector().getSelectedIndex() >= ls.getNormalLevelCount()){	// Wenn custom level gewählt wird
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
				}else{	// ansonsten wenn standart level ausgewählt wird
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
