package Pacman.gui;

import java.awt.Color;

import Pacman.gfx.Assets;
import Pacman.stats.Stats;

public class MainMenu {
	
	private Display display;
	private int height, width, componentDistance;
	
	private MenuLabel titlePic;
	private MenuButton play, score, editor;
	
	public MainMenu(Display display){
		this.display = display;
		this.height = display.getHeight();
		this.width = display.getWidth();
		this.componentDistance = display.getComponentDistance();

		initMenu();
		display.getFrame().setVisible(true);
	}

	private void initMenu(){
		display.getFrame().getContentPane().removeAll();
		display.getFrame().getContentPane().setLayout(null);
		display.getFrame().setSize(display.getWidth(), display.getHeight());
		display.getFrame().getContentPane().setBackground(Color.BLACK);
		display.getFrame().setLocationRelativeTo(null);
		initButtons();
	}
	
	private void initButtons(){
		titlePic = new MenuLabel((width / 2 - Assets.title.getWidth() / 2), componentDistance, Assets.title);
		play = new MenuButton(display, (width / 2 - Assets.play.getWidth() / 2), titlePic.y + titlePic.getHeight() + componentDistance , Assets.play, Assets.playOutline);
		play.setName("play");
		score = new MenuButton(display, (width / 2 - Assets.score.getWidth() / 2), play.y + play.getHeight() + componentDistance, Assets.score, Assets.scoreOutline);
		score.setName("score");
		editor = new MenuButton(display, (width / 2 - Assets.editor.getWidth() / 2), score.y + score.getHeight() + componentDistance, Assets.editor, Assets.editorOutline);
		editor.setName("editor");
		
		

		
		
		display.getFrame().add(titlePic);
		display.getFrame().add(play);
		display.getFrame().add(score);
		display.getFrame().add(editor);
		
		display.getFrame().repaint();
	}
	
}
