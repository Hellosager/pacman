package Pacman.gui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import Pacman.input.MouseInput;

public class MenuButton extends MenuLabel{
	private ImageIcon[] images;
	private static final int numberImages = 2;
	private int currentImage = 0;
	private Display display;

	public MenuButton(Display display, int x, int y, BufferedImage image, BufferedImage hoverImage) {
		super(x, y, image);
		this.display = display;
		images = new ImageIcon[numberImages];
		images[0] = new ImageIcon(image);
		images[1] = new ImageIcon(hoverImage);
		this.addMouseListener(new MouseInput(this, display));
	}

	public void changeImage(){
		if(currentImage == 0){
			currentImage = 1;
			this.setIcon(images[currentImage]);
			this.repaint();
		}
		else if(currentImage == 1){
			currentImage = 0;
			this.setIcon(images[currentImage]);
			this.repaint();
		}
	}
	
	public Display getDisplay(){
		return display;
	}
	
}
