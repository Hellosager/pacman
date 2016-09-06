package Pacman.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	public static BufferedImage play, playOutline, score, scoreOutline, title, editor, editorOutline;
	public static BufferedImage wall, fullWay, emptyWay, raster;
	public static BufferedImage inky, blinky, pinky, pacman;
	
	public static void initAssets(){
		title = ImageLoader.loadImage("/images/title.png");
		play = ImageLoader.loadImage("/images/play.png");
		playOutline = ImageLoader.loadImage("/images/playOutline.png");
		score = ImageLoader.loadImage("/images/score.png");
		scoreOutline = ImageLoader.loadImage("/images/scoreOutline.png");
		editor = ImageLoader.loadImage("/images/editor.png");
		editorOutline = ImageLoader.loadImage("/images/editorOutline.png");
		
		wall = ImageLoader.loadImage("/images/wall.png");
		fullWay = ImageLoader.loadImage("/images/fullWay.png");
		emptyWay = ImageLoader.loadImage("/images/emptyWay.png");
		raster = ImageLoader.loadImage("/images/raster.png");
		
		inky = ImageLoader.loadImage("/images/blueGhost.png");	// Blau
		blinky = ImageLoader.loadImage("/images/redGhost.png");	// Rot
		pinky = ImageLoader.loadImage("/images/pinkGhost.png");	// Pink
		pacman = ImageLoader.loadImage("/images/pacman.png");
	}
	
}
