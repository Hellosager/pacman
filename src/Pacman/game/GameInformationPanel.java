package Pacman.game;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Pacman.creatures.Creature;
import Pacman.gfx.Assets;

public class GameInformationPanel extends JPanel{
	private static final int LIFE_COUNT = 3;
	private JLabel[] lifes; 
	private JLabel scoreLabel;

	public GameInformationPanel(){
		this.setLayout(new GridLayout(1, 2));
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(new LineBorder(Color.BLACK, 3));

		JPanel leftPanel = new JPanel(new GridLayout(1,3));
		leftPanel.setOpaque(false);
		// LeftPanel mit lifes initalisieren
		lifes = new JLabel[LIFE_COUNT];
		for(int i = 0; i < lifes.length; i++){
			lifes[i] = new JLabel(new ImageIcon(Assets.pacmanDircetions[Creature.RIGHT][0]));
			leftPanel.add(lifes[i]);
		}

		// Rightpanel initalisieren
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		scoreLabel = new JLabel("Score:          " + "0");		//10 Whitespaces
		scoreLabel.setForeground(Color.ORANGE);
		rightPanel.add(scoreLabel);
		
		
		this.add(leftPanel);
		this.add(rightPanel);
	}
	
	public JLabel[] getLifes(){
		return lifes;
	}
	
	public JLabel getScoreLabel(){
		return scoreLabel;
	}
}
