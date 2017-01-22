package Pacman.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Date;

import Pacman.creatures.Ghost;
import Pacman.creatures.Player;
import Pacman.gui.Display;
import Pacman.level.Level;
import Pacman.steuerung.Steuerung;
import Pacman.tiles.Tile;

public class Game implements Runnable{
	private boolean running = false;
	private boolean levelIsPlayed = false;
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
		
		while(levelIsPlayed){
			for(int number = 3; number > 0; number--){
				if(!levelIsPlayed)
					break;
				renderCountdown(number);
			}
			
			running = true;
			while(running){
				if(modeTime == null)
					modeTime = new Date();
//			long start = System.nanoTime();
				if(!paused){
					tick();
					render();
					checkCollision();
				}
				if((new Date().getTime() - modeTime.getTime()) >= 15000 && !paused){
					modeTime = null;
					level.changeModes();
				}
//			long end = System.nanoTime();
//			System.out.println("Gebraucht für gamelopp: " + (end - start));
				try {Thread.sleep(50);}catch(InterruptedException e){}				
			}
			
			modeTime = null;
			level.resetSpawns();
			try {Thread.sleep(1500);} catch (InterruptedException e) {}
			if(levelIsPlayed)
				render();
		}
	}
	
	
	private void init(){
		score = 0;
		initGhostPaths();
		
		width = display.getWidth();
		height = display.getHeight();	
		display.getFrame().getContentPane().removeAll();
		display.getFrame().getContentPane().setBackground(null);
		display.getFrame().setLayout(new BorderLayout());
		
		
		gi = new GameInformationPanel();
		lifeCount = gi.getLifes().length;
		
		
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
		if(levelIsPlayed == false)
			levelIsPlayed = true;
		new Thread(this).start();
	}
	
	public synchronized void stop(){
		if(levelIsPlayed == true){
			running = false;
			levelIsPlayed = false;
		}
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
				(player.getRenderY() % Tile.TILEHEIGHT == 0)){
				running = false;
				levelIsPlayed = false;
			}
	}
	
	public void render(){
		bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(3);
			bs = canvas.getBufferStrategy();
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
	
	public void renderCountdown(int number){
		render();
		Graphics g = canvas.getGraphics();
		g.setColor(Color.RED);
		switch(number){
		case 3:
			g.fillRect(9*Tile.TILEWIDTH, 7*Tile.TILEHEIGHT, 7*Tile.TILEWIDTH , Tile.TILEHEIGHT);
			g.fillRect(10*Tile.TILEWIDTH, 11*Tile.TILEHEIGHT, 6*Tile.TILEWIDTH , Tile.TILEHEIGHT);
			g.fillRect(9*Tile.TILEWIDTH, 15*Tile.TILEHEIGHT, 7*Tile.TILEWIDTH , Tile.TILEHEIGHT);
			g.fillRect(15*Tile.TILEWIDTH, 7*Tile.TILEHEIGHT, Tile.TILEWIDTH , 9*Tile.TILEHEIGHT);
			break;
			
		case 2:
			g.fillRect(9*Tile.TILEWIDTH, 7*Tile.TILEHEIGHT, 7*Tile.TILEWIDTH , Tile.TILEHEIGHT);
			g.fillRect(15*Tile.TILEWIDTH, 8*Tile.TILEHEIGHT, Tile.TILEWIDTH, 3*Tile.TILEHEIGHT);
			g.fillRect(9*Tile.TILEWIDTH, 11*Tile.TILEHEIGHT, 7*Tile.TILEWIDTH , Tile.TILEHEIGHT);
			g.fillRect(9*Tile.TILEWIDTH, 12*Tile.TILEHEIGHT, Tile.TILEWIDTH , 3*Tile.TILEHEIGHT);
			g.fillRect(9*Tile.TILEWIDTH, 15*Tile.TILEHEIGHT, 7*Tile.TILEWIDTH , Tile.TILEHEIGHT);
			break;
			
		case 1: 
			g.fillRect(15*Tile.TILEWIDTH, 7*Tile.TILEHEIGHT, Tile.TILEWIDTH , 9*Tile.TILEHEIGHT);
			break;
		}
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	
	public void setLevel(Level level){
		this.level = level;
		this.player = level.getPlayer();
	}
	
	public Level getLevel(){
		return level;
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
	
	private void loseLife(){
		if(lifeCount != 0)
			gi.getLifes()[lifeCount-1].setIcon(null);
		lifeCount--;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	private void checkCollision(){
		Player p = level.getPlayer();
		Ghost[] ghosts = level.getGhosts();
		for(Ghost g : ghosts){
			if(p.getHitbox().intersects(g.getHitbox())){
				loseLife();
				running = false;
				if(lifeCount < 0)
					levelIsPlayed = false;
			}
		}
	}
}
