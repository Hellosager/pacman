package Pacman.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		String levelDatei = ((ls.getLevelSelector().getSelectedIndex())) < (ls.getNormalLevelCount()) ? "level/" : "Level/";
		levelDatei = levelDatei +  ls.getFileNames().get(ls.getLevelSelector().getSelectedIndex());
		switch(e.getActionCommand()){
			case "select":
				previewLevel = new Level(levelDatei);
				ls.getPreviewCanvas().setCurrentRenderLevel(previewLevel);
				ls.getPreviewCanvas().repaint();
				break;
				
			case "menu":
				new MainMenu(ls.getDisplay());
				break;
			
			default:	// Play
				Game g = new Game(ls.getDisplay());
				Level level = new Level(levelDatei);
				if(level.isValidToPlay(ls.getDisplay().getFrame())){
					g.setLevel(level);
					g.start();
				}
				break;
		}
	}	
}
