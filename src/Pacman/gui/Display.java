package Pacman.gui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import Pacman.gfx.Assets;
import Pacman.stats.StatNames;
import Pacman.stats.Stats;

public class Display {
	
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 600;
	private static final int COMPONENT_DISTANCE =  (GAME_HEIGHT - 290) / 5 ;
	private static final Dimension GAME_DIM = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	
	private JFrame frame;
	private Stats stats = new Stats();



	public Display(String title) {		
		createDisplay(title);
	}

	private void createDisplay(String title){
		frame = new JFrame(title);
		frame.setLayout(null);
		frame.setSize(GAME_DIM);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we){
				stats.saveStats();
				System.exit(0);
			}
		});
		Assets.initAssets();
		
		new MainMenu(this);

	}
		
	public JFrame getFrame(){
		return frame;
	}

	public int getWidth(){
		return GAME_WIDTH;
	}

	public int getHeight(){
		return GAME_HEIGHT;
	}
	
	public int getComponentDistance(){
		return COMPONENT_DISTANCE;
	}
	
	public Stats getStats(){
		return stats;
	}
	
}
