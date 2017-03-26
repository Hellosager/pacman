package Pacman.stats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Pacman.creatures.Creature;
import Pacman.gui.Display;
import Pacman.gui.MainMenu;

public class StatsView {
	private Display display;
	private JFrame frame;
	private Stats stats;
	private JPanel wrapper;
	private ColorSwitcher cs;

	public StatsView(Display display) {
		this.display = display;
		this.frame = display.getFrame();
		this.stats = display.getStats();
		
		initGui();
		cs.start();
	}

	private void initGui() {
		Map statsMap = stats.getStatsMap();
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		JLabel head = new JLabel("Stats", JLabel.CENTER);
		head.setForeground(Color.BLACK);
		head.setBackground(Color.decode("#9985A8"));
		head.setOpaque(true);
		head.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
		head.setFont(new Font("Arial", Font.BOLD, 110));
		frame.add(head, BorderLayout.NORTH);
		
		int rows = stats.DEFAULT_STAT_NAMES.length;
		wrapper = new JPanel(new GridLayout(rows, 1, 0, 0));
		cs = new ColorSwitcher(wrapper);
		wrapper.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 5, Color.BLACK));
		for(String key : Stats.DEFAULT_STAT_NAMES){
			JPanel statPanel = new StatsPanel(key, statsMap.get(key).toString()).getWrapper();
			wrapper.add(statPanel);
		}
		addMenuButton();
		
		frame.add(wrapper);
		frame.repaint();
		frame.revalidate();
	}

	private void addMenuButton() {
		JLabel menu = new JLabel("Menu", JLabel.CENTER);
		menu.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 5, Color.BLACK));
		menu.setBackground(Color.GRAY);
		menu.setOpaque(true);
		menu.setFont(new Font("Arial", Font.BOLD, 25));
		Color mouseOver = new Color(142, 78, 163);
		menu.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				cs.stop();
				new MainMenu(display);
			}
			public void mouseExited(MouseEvent e) {
				menu.setBackground(Color.GRAY);
			}
			public void mouseEntered(MouseEvent e) {
				menu.setBackground(mouseOver);
			}
		});		
		frame.add(menu, BorderLayout.SOUTH);
	}

	
}
