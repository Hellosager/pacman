package Pacman.editor;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Pacman.editor.input.SpawnManagerInput;
import Pacman.gfx.Assets;

public class SpawnManager {
	
	
	private JLabel[] pics = {	new JLabel(new ImageIcon(Assets.pacman)),
								new JLabel(new ImageIcon(Assets.blinky)),
								new JLabel(new ImageIcon(Assets.pinky)),
								new JLabel(new ImageIcon(Assets.inky))};
	
	private JTextField[][] textfields = new JTextField[4][2];

	public SpawnManager(JFrame mainframe, Editor editor) {
		JFrame f = new JFrame();
		f.setResizable(false);
		f.setLocationRelativeTo(mainframe);
		f.setLayout(new FlowLayout());	// 4 reihen, 2 spalten
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setVisible(true);
		
		JPanel spawns = new JPanel();
		spawns.setLayout(new GridLayout(6,3,5,2));	//6 reihen, 3 Spalten, 5 nach unten, 2 nach rechts
		
		spawns.add(new JLabel());
		spawns.add(new JLabel("       X"));
		spawns.add(new JLabel("       Y"));

		
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
			case 0: textfields[i][k].setText(editor.getEditMap().getCreatures()[i].getSpawnX() + ""); break;
			case 1: textfields[i][k].setText(editor.getEditMap().getCreatures()[i].getSpawnY() + ""); break;
			}
			spawns.add(textfields[i][k]);	
			}
		}

		spawns.add(new JLabel());
		JButton b = new JButton("Set");
		b.addActionListener(new SpawnManagerInput(this, mainframe, f, editor));
		spawns.add(b);
		spawns.add(new JLabel());

		
		f.add(spawns);
		f.pack();
	}
	
	public JTextField[][] getTextfields(){
		return textfields;
	}
	
}
