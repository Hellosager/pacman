package Pacman.gui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MenuLabel extends JLabel{
	protected int x, y;
	protected BufferedImage image;
	protected ImageIcon icon;
	
	public MenuLabel(int x, int y, BufferedImage image){
		this.x = x;
		this.y = y;
		this.image = image;
		
		icon = new ImageIcon(image);
		setIcon(icon);

		setBounds(x, y, image.getWidth(), image.getHeight());
		setOpaque(false);
	}
	
	
}
