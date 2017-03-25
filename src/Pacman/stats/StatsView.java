package Pacman.stats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Pacman.gui.Display;

public class StatsView {
	private Display display;
	private JFrame frame;
	private Stats stats;

	public StatsView(Display display) {
		this.display = display;
		this.frame = display.getFrame();
		
		initGui();
	}

	private void initGui() {
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		JLabel head = new JLabel("Stats", JLabel.CENTER);
		head.setForeground(Color.RED);
		head.setFont(new Font("Arial", Font.BOLD, 110));
		frame.add(head, BorderLayout.NORTH);
		
		JPanel wrapper = new JPanel(new GridLayout(10, 1, 0, 10));
		wrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		wrapper.add(new JLabel());
		for(int i = 1; i < 10; i++){
			wrapper.add(new StatsPanel("Statname " + i, "Value").getWrapper());
		}
		frame.add(wrapper);
		frame.repaint();
		frame.revalidate();
	}

	
}
