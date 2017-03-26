package Pacman.stats;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsPanel {
	private static final Font font = new Font("Arial", Font.BOLD, 18);
	private static final SimpleDateFormat timePlayedFormat = new SimpleDateFormat("HH:mm:ss");

	private JPanel wrapper;
	
	public StatsPanel(String statName, String value){
		this.wrapper = new JPanel(new GridLayout(1, 2));
		timePlayedFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		JLabel nameLabel = new JLabel(statName, JLabel.CENTER);
		JLabel valueLabel;
		if(statName.equals(StatNames.PLAYED_TIME))
			valueLabel = new JLabel(timePlayedFormat.format(new Date(new Long(value))), JLabel.CENTER);
		else
			valueLabel = new JLabel(value, JLabel.CENTER);
		nameLabel.setFont(font);
		nameLabel.setOpaque(false);
		valueLabel.setFont(font);
		valueLabel.setOpaque(false);
		
		wrapper.setOpaque(false);
		wrapper.add(nameLabel);
		wrapper.add(valueLabel);
	}
	
	public JPanel getWrapper(){
		return wrapper;
	}
	
}
