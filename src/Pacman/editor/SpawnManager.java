package Pacman.editor;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Pacman.creatures.Creature;
import Pacman.editor.input.SpawnManagerInput;
import Pacman.gfx.Assets;

public class SpawnManager {
	
	
	private JLabel[] pics = {
								new JLabel(new ImageIcon(Assets.blinky[0])),
								new JLabel(new ImageIcon(Assets.pinky[0])),
								new JLabel(new ImageIcon(Assets.inky[0]))};
	
	private JTextField[][] textfields = new JTextField[4][2];
	private JTextField playerX, playerY;

	public SpawnManager(JFrame mainframe, Editor editor) {
		JDialog spawnDialog = new JDialog(mainframe);
		spawnDialog.setResizable(false);
		spawnDialog.setLocationRelativeTo(mainframe);
		spawnDialog.setLayout(new FlowLayout());	// 4 reihen, 2 spalten
		spawnDialog.setVisible(true);
		
		JPanel spawns = new JPanel();
		spawns.setLayout(new GridLayout(6,3,5,2));	//6 reihen, 3 Spalten, 5 nach unten, 2 nach rechts
		
		spawns.add(new JLabel());
		spawns.add(new JLabel("       X"));
		spawns.add(new JLabel("       Y"));
		
		// TODO refactor dat shit
		spawns.add(new JLabel(new ImageIcon(Assets.pacmanDirections[Creature.RIGHT][0])));
		playerX = new JTextField();

		playerX.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {}
			public void focusGained(FocusEvent e) {
				playerX.selectAll();
			}
		});
		playerY = new JTextField();
		playerY.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {}
			public void focusGained(FocusEvent e) {
				playerY.selectAll();
			}
		});
		
		playerX.setText(editor.getEditMap().getPlayer().getSpawnX() + "");
		playerY.setText(editor.getEditMap().getPlayer().getSpawnY() + "");
		spawns.add(playerX);
		spawns.add(playerY);
		
		for(int i = 0; i < pics.length; i++){	//Bilder
			spawns.add(pics[i]);
			for(int k = 0; k < textfields[i].length; k++){
				JTextField tf = new JTextField();
				textfields[i][k] = tf;
				textfields[i][k].addFocusListener(new FocusListener() {
					public void focusLost(FocusEvent e) {}				
					public void focusGained(FocusEvent e) {
						tf.selectAll();
					}
				});
	
				switch(k){
				case 0: textfields[i][k].setText(editor.getEditMap().getGhosts()[i].getSpawnX() + ""); break;
				case 1: textfields[i][k].setText(editor.getEditMap().getGhosts()[i].getSpawnY() + ""); break;
				}
				spawns.add(textfields[i][k]);	
				}
		}

		spawns.add(new JLabel());
		JButton b = new JButton("Set");
		b.addActionListener(new SpawnManagerInput(this, mainframe, spawnDialog, editor));
		spawns.add(b);
		spawns.add(new JLabel());

		
		spawnDialog.add(spawns);
		spawnDialog.pack();
	}
	
	public JTextField[][] getTextfields(){
		return textfields;
	}
	
	public JTextField getPlayerX(){
		return playerX;
	}
	
	public JTextField getPlayerY(){
		return playerY;
	}
	
}
