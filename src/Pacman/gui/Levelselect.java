package Pacman.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Pacman.gfx.ImageLoader;
import Pacman.input.LevelSelectListener;

public class Levelselect {
	
	private static final int normalLevelCount = 5;
	
	private Display display;
	private int height, width;
	private JComboBox levelSelector;
	private JLabel levelPreview;
	private JButton play, menu;

	
	public Levelselect(Display display) {
		this.display = display;
		this.height = display.getHeight();
		this.width = display.getWidth();
		
		initGui();
	}
	
	private void initGui(){
		display.getFrame().getContentPane().removeAll();
		display.getFrame().getContentPane().setBackground(Color.BLACK);
		display.getFrame().setLayout(new BorderLayout());
	
		levelPreview = new JLabel(new ImageIcon(ImageLoader.loadImage("/images/levelPreviews/level1preview.png")));

		levelSelector = new JComboBox();
		levelSelector.setActionCommand("select");
		// Hier soll die Anzahl der files in res/level ausgelesen werden
		for(int i = 0; i < normalLevelCount; i++)	// hardcoded alle norameln level
			levelSelector.addItem("Level " + (i+1));

		// Dann alle eigenen Level
		File f = new File("Level");
		if(f.exists())
		for(int i = 0; i < f.listFiles().length; i++){
			String s = f.listFiles()[i].getName();
			levelSelector.addItem("Custom Level: " + s.substring(0, s.lastIndexOf(".")));
		}
		
		levelSelector.addActionListener(new LevelSelectListener(this));

		
		play = new JButton("Play");
		play.setActionCommand("play");
		play.addActionListener(new LevelSelectListener(this));
		
		menu = new JButton("<<< Menu");
		menu.setActionCommand("menu");
		menu.addActionListener(new LevelSelectListener(this));
		
		display.getFrame().add(levelSelector, BorderLayout.NORTH);
		display.getFrame().add(levelPreview);
		
		JPanel southBar = new JPanel(new GridLayout(1, 4, 30, 0));
		southBar.setBackground(Color.BLACK);
		southBar.add(new JLabel());
		southBar.add(menu);
		southBar.add(play);
		southBar.add(new JLabel());
		display.getFrame().add(southBar, BorderLayout.SOUTH);
		
		display.getFrame().repaint();
		display.getFrame().revalidate();
	}
	
	private void initLevel(){
		
	}
	
	public JComboBox getLevelSelector(){
		return levelSelector;
	}
	
	public JLabel getLevelPreview(){
		return levelPreview;
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public int getNormalLevelCount(){
		return normalLevelCount;
	}
}
