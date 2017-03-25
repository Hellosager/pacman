package Pacman.stats;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsPanel {
	private JPanel wrapper;
	
	public StatsPanel(String statName, String value){
		this.wrapper = new JPanel(new GridLayout(1, 2));
		JLabel nameLabel = new JLabel(statName, JLabel.CENTER);
		JLabel valueLabel = new JLabel(value, JLabel.CENTER);
		
		wrapper.add(nameLabel);
		wrapper.add(valueLabel);
	}
	
	public JPanel getWrapper(){
		return wrapper;
	}
	
}
