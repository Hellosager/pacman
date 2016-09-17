package Pacman.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Pacman.input.LevelSelectListener;
import Pacman.level.Level;

public class Levelselect {
	
	private static final int normalLevelCount = 5;
	
	private Display display;
	private int height, width;
	private JComboBox levelSelector;
	private PreviewCanvas previewCanvas;
	private JButton play, menu;

	
	public Levelselect(Display display) {
		this.display = display;
		this.height = display.getHeight();
		this.width = display.getWidth();
		
		initGui();
	}
	
	private void initGui(){
		
		display.getFrame().getContentPane().removeAll();
		display.getFrame().getContentPane().setBackground(Color.WHITE);
		display.getFrame().setLayout(new BorderLayout());
		display.getFrame().setLocation(display.getFrame().getX(),display.getFrame().getY()-30);
	
//		levelPreview = new JLabel(new ImageIcon(ImageLoader.loadImage("/images/levelPreviews/level1preview.png")));
		previewCanvas = new PreviewCanvas(new Level("level/level1.txt"));
		Dimension dim = new Dimension(width, height);
		previewCanvas.setPreferredSize(dim);
		previewCanvas.setMaximumSize(dim);
		previewCanvas.setMinimumSize(dim);

		
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
		display.getFrame().add(previewCanvas);
		
		JPanel southBar = new JPanel(new GridLayout(1, 4, 30, 0));
		southBar.setBackground(new Color(63, 52, 52));
		southBar.add(new JLabel());
		southBar.add(menu);
		southBar.add(play);
		southBar.add(new JLabel());
		display.getFrame().add(southBar, BorderLayout.SOUTH);
		
		display.getFrame().pack();
		display.getFrame().repaint();
		display.getFrame().revalidate();
	}
	
	public JComboBox getLevelSelector(){
		return levelSelector;
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public int getNormalLevelCount(){
		return normalLevelCount;
	}
	
	public PreviewCanvas getPreviewCanvas(){
		return previewCanvas;
	}
}
