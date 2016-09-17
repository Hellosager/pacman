package Pacman.gui;

import java.awt.Graphics;

import javax.swing.JComponent;

import Pacman.level.Level;

public class PreviewCanvas extends JComponent{
	private Level currentRenderLevel;
	
	public PreviewCanvas(Level currentRenderLevel) {
		this.currentRenderLevel = currentRenderLevel;
	}
	
	public void paint(Graphics g){
		currentRenderLevel.render(g);
	}
	
	public void setCurrentRenderLevel(Level currentRenderLevel){
		this.currentRenderLevel = currentRenderLevel;
	}
}
