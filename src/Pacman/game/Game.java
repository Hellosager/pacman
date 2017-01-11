package Pacman.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.time.LocalTime;
import java.util.Date;

import Pacman.creatures.Ghost;
import Pacman.creatures.Player;
import Pacman.gui.Display;
import Pacman.level.Level;
import Pacman.steuerung.Steuerung;
import Pacman.tiles.Tile;

public class Game implements Runnable{
	private boolean running = false;
	private boolean paused = false;
	private int width, height;
	
	private Display display;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	private Level level;
	private GameInformationPanel gi;
	private Player player;
	private Date modeTime;
	private int score, lifeCount;	// score und anzahl der verbleibenden leben

	public Game(Display display) {
		this.display = display;
	}
	
	@Override
	// Gameloop
	public void run() {
		init();
		canvas.requestFocus();

		
		while(running){
			if(modeTime == null)
				modeTime = new Date();
//			long start = System.nanoTime();
			if(!paused){
				tick();
				render();
			}
			if((new Date().getTime() - modeTime.getTime()) >= 15000){
				modeTime = null;
				level.changeModes();
			}
//			long end = System.nanoTime();
//			System.out.println("Gebraucht für gamelopp: " + (end - start));
			try {Thread.sleep(50);}catch(InterruptedException e){}				
		}
	}
	
	
	private void init(){
		score = 0;
		lifeCount = 3;
		initGhostPaths();
		
		width = display.getWidth();
		height = display.getHeight();	
		display.getFrame().getContentPane().removeAll();
		display.getFrame().getContentPane().setBackground(null);
		display.getFrame().setLayout(new BorderLayout());
		
		
		gi = new GameInformationPanel();
		
		
		canvas = new Canvas();
		Dimension dim = new Dimension(width, height);
		canvas.setPreferredSize(dim);
		canvas.setMaximumSize(dim);
		canvas.setMinimumSize(dim);
//		canvas.setFocusable(false);
		canvas.addKeyListener(new Steuerung(level.getPlayer(), this));

		
		display.getFrame().add(gi, BorderLayout.NORTH);
		display.getFrame().add(canvas);
		display.getFrame().pack();
		display.getFrame().repaint();
		display.getFrame().revalidate();
		render();
	}

	private void initGhostPaths() {
		for(Ghost g : level.getGhosts()){
			g.updateFields();
		}
	}

	public synchronized void start(){
		if(running == false)
			running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop(){
		if(running == true)
			running = false;
		else return;
		try {
			new Thread(this).join();
		}catch(InterruptedException e){}
	}
	
	public void tick(){
		level.tick();
		gi.getScore().setText("Score:          " + (score + level.getLevelScore()));
		if(level.getLevelScore() >= level.getFullWayCount())
			if(	(player.getRenderX() % Tile.TILEWIDTH == 0) &&
				(player.getRenderY() % Tile.TILEHEIGHT == 0))
					running = false;
	}
	
	public void render(){
		bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		// Draw
		// Bei 600x 600 : xMax = 550, yMax = 575
//		Tile.tileTextures[0].render(g, 0, 0);
		// Muss Level rendern, Player, 3 Ghosts
		level.render(g);
		
		bs.show();
		g.dispose();
	}
	
	
	public void setLevel(Level level){
		this.level = level;
		this.player = level.getPlayer();
	}
	
	public void setPaused(boolean paused){
		this.paused = paused;
	}
	
	public boolean isPaused(){
		return paused;
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public Date getModeTime(){
		return modeTime;
	}
	
	public void setModeTime(Date modeTime){
		this.modeTime = modeTime;
	}
}
