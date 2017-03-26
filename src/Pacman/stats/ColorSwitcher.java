package Pacman.stats;

import java.awt.Color;

import javax.swing.JPanel;

public class ColorSwitcher implements Runnable{
	private static final Color c1 = Color.decode("#90C3D4");
	private static final Color c2 = Color.decode("#D4A190");
	private static final Color c3 = Color.decode("#C390D4");
	private static final Color c4 = Color.decode("#9E8280");
	private static final Color c5 = Color.decode("#3FA8B0");
	private static final Color c6 = Color.decode("#B8B85A");
	
	private Color[] colors;
	private Color currentColor;
	private int indexOfDestinationColor;
	private Color destinationColor;
	private int r, g, b;
	
	private boolean isRunning = false;
	private JPanel wrapper;
	
	public ColorSwitcher(JPanel wrapper){
		this.wrapper = wrapper;
		colors = new Color[]{c1, c2, c3, c4, c5, c6};
		currentColor = colors[0];
		indexOfDestinationColor = 1;
		destinationColor = colors[indexOfDestinationColor];
		r = currentColor.getRed();
		g = currentColor.getGreen();
		b = currentColor.getBlue();
	}
	
	@Override
	public void run() {
		while(isRunning){
			calcRGB();
			currentColor = new Color(r, g, b);
			wrapper.setBackground(currentColor);
			handleDestinationColor();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if(!isRunning){
			isRunning = true;
			new Thread(this).start();
		}
	}
	
	public void stop() {
		if(isRunning){
			isRunning = false;
			try {
				new Thread(this).join();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	
	private void calcRGB(){
		if(r < destinationColor.getRed())
			r++;
		else if(r > destinationColor.getRed())
			r--;
		if(g < destinationColor.getGreen())
			g++;
		else if(g > destinationColor.getGreen())
			g--;
		if(b < destinationColor.getBlue())
			b++;
		else if(r > destinationColor.getBlue())
			b--;
	}
	
	private void handleDestinationColor(){
//		if(r == destinationColor.getRed() && g == destinationColor.getGreen() && b == destinationColor.getBlue())
		if(currentColor.getRGB() == destinationColor.getRGB())
			if(indexOfDestinationColor == colors.length-1){
				indexOfDestinationColor = 0;
				destinationColor = colors[indexOfDestinationColor];
			}
			else{
				indexOfDestinationColor++;	
				destinationColor = colors[indexOfDestinationColor];
			}
	}
	
}
