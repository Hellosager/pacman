package Pacman.level;


import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Pacman.Utils;
import Pacman.creatures.Blinky;
import Pacman.creatures.Creature;
import Pacman.creatures.Ghost;
import Pacman.creatures.Inky;
import Pacman.creatures.Pinky;
import Pacman.creatures.Player;
import Pacman.gfx.Assets;
import Pacman.level.pathfinder.Dijkstra;
import Pacman.tiles.Tile;

public class Level {

	private static final String DIR_NAME = "Level";
	private static int FULL_WAY_COUNT;
	
	private int width, height;
	private Ghost[] ghosts = {	new Blinky(Assets.blinky, this),
								new Pinky(Assets.pinky, this),
								new Inky(Assets.inky, this)};
	private Player player = new Player(Assets.pacmanDircetions[Creature.RIGHT][0], this);
	private int[][] tileMap;
	private Point[] allWayTiles;
	private String levelName;
	private boolean showDestinations = false;
	private int levelScore = 0;
	
	
	
	public Level(String path) {
		loadLevel(path);
		int fullWayCount = 0;
		List<Point> allWayTiles = new ArrayList<Point>();
		for(int x = 0; x < tileMap.length; x++)
			for(int y = 0; y < tileMap[x].length; y++){
				if(tileMap[x][y] == Tile.EMPTY_WAY)
					allWayTiles.add(new Point(x, y));
				if(tileMap[x][y] == Tile.FULL_WAY){
					allWayTiles.add(new Point(x, y));
					fullWayCount++;
				}
			}
		this.allWayTiles = new Point[allWayTiles.size()];
		this.allWayTiles = allWayTiles.toArray(this.allWayTiles);
		FULL_WAY_COUNT = fullWayCount;
	}
	
	public void loadLevel(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		player.setSpawnX(Utils.parseInt(tokens[2]));
		player.setSpawnY(Utils.parseInt(tokens[3]));
		
		int tk = 3;
		for(Ghost c : ghosts){
			tk++;
			c.setSpawnX(Utils.parseInt(tokens[tk]));
			tk++;
			c.setSpawnY(Utils.parseInt(tokens[tk]));
		}
			
		tileMap = new int[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++){
				tileMap[x][y] = Utils.parseInt(tokens[(x + y * width) + 10]); // 10 = Anzahl der Werte bevor Tilemap beginnt
			}
				
	}
	
	// LEVEL SPEICHERN
	public void saveLevel(String levelName){
		File dir = new File(DIR_NAME);
		if(!dir.exists())
			dir.mkdir();
//		if(!levelFile.exists())
			try {
				File levelFile = new File(DIR_NAME + "/" + levelName + ".txt");
				levelFile.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(levelFile));
				writer.write(width + " " + height + "\n");
				writer.write(player.getSpawnX() + " " + player.getSpawnY() + "\n");
				for(int i = 0; i < ghosts.length; i++)
					writer.write(ghosts[i].getSpawnX() + " " + ghosts[i].getSpawnY() + "\n");
				writer.newLine();
				
				for(int y = 0; y < height; y++){
					for(int x = 0; x < width; x++)
						writer.write(tileMap[x][y] + " ");
				writer.newLine();
				}
						
				
				
				writer.flush();
				writer.close();
			} catch (IOException e) {	e.printStackTrace();	}
	}
	
	public Tile getTile(int x, int y){
		Tile t = Tile.tileTextures[tileMap[x][y]];
		if(t == null){
			return Tile.wallTile; 
		}
		return t;
	}
	
	public void render(Graphics g){
		for(int y = 0; y < height; y++)	// Tiles render
			for(int x = 0; x < width; x++){
				getTile(x, y).render(g, (x*Tile.TILEWIDTH), (y*Tile.TILEHEIGHT));
			}
		
		// Creatures rendern
		for(int i = 0; i < ghosts.length; i++)
			if(ghosts[i].isRenderable())
		ghosts[i].render(g);
		if(player.isRenderable())
			player.render(g);
	}
	
	public void tick(){
		player.tick();
		for(Ghost c : ghosts){
			c.tick();
		}
		
		int x = 0, y = 0;
		if(player.getDircetion() == Creature.RIGHT || player.getDircetion() == Creature.DOWN){
			x = (player.getRenderX()-20) / Tile.TILEWIDTH +1;
			y = (player.getRenderY() - 20) / Tile.TILEHEIGHT+1;			
		}else{
			x = (player.getRenderX() + 20) / Tile.TILEWIDTH;
			y = (player.getRenderY() + 20) / Tile.TILEHEIGHT;						
		}
		if(coordIsFullWay(x, y)){
			tileMap[x][y] = Tile.EMPTY_WAY;
			updateDestinations();
			levelScore++;
		}
	}
	
	public int[][] getTileMap(){
		return tileMap;
	}
	
	public void setLevelName(String levelName){
		this.levelName = levelName;
	}
	
	public boolean exists(){
		File levelFile = new File(DIR_NAME + "/" + levelName + ".txt");
		return levelFile.exists();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Ghost[] getGhosts(){
		return ghosts;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getLevelScore(){
		return levelScore;
	}
	
	public int getFullWayCount(){
		return FULL_WAY_COUNT;
	}
	
	public boolean isValidToPlay(){
		if(spawnsAreValid() && allTilesAreSet() && necessaryWallsAreSet() && isFinishable() )
			return true;
		return false;
	}
	
	public void showFailureInfo(JFrame frame){
		if(!spawnsAreValid())
			JOptionPane.showMessageDialog(frame, "Spawns are not set properly");
		else if(!allTilesAreSet())
			JOptionPane.showMessageDialog(frame, "There are tiles that are not set");
		else if(!necessaryWallsAreSet())
			JOptionPane.showMessageDialog(frame, "Edge Walls are not set. You shouldn't change the files ;)");
		else if(!isFinishable())
			JOptionPane.showMessageDialog(frame, "There are unreachable waytiles");

	}
	
	private boolean spawnsAreValid(){
		for(int i = 0; i < ghosts.length; i++)
			if((ghosts[i].getSpawnX() == 0) || (ghosts[i].getSpawnY() == 0)){	// Wenn irgendeine creature 0,0 koords hat == false
				return false;
			}
		return true;		
	}

	private boolean allTilesAreSet(){
		for(int x = 0; x < tileMap.length; x++)	// Wenn irgendein tile == 3 ist dann false
			for(int y = 0; y < tileMap[x].length; y++)
				if(tileMap[x][y] == 3){
					return false;
				}
		return true;
	}

	private boolean isFinishable(){
		Dijkstra pathfinder = new Dijkstra(this);
		
		for(int x = 0; x < tileMap.length; x++)	// gehe x durch
			for(int y = 0; y < tileMap[x].length; y++)	// gehe y durch
				if((tileMap[x][y] == 1) || (tileMap[x][y] == 2))	
					pathfinder.findPath(ghosts[0].getSpawnX(), ghosts[0].getSpawnY(), x, y);
				
		if(!pathfinder.allNodesAreReachable()){
			return false;
		}
		return true;
	}
	
	private boolean necessaryWallsAreSet(){
//		System.out.println("necassrayWalls");
			for(int x = 0; x <= tileMap.length; x+=23)
				for(int y = 0; y < tileMap[x].length; y++)
					if(tileMap[x][y] != 0)
						return false;
			for(int y = 0; y <= tileMap.length; y+=23)
				for(int x = 0; x < tileMap[y].length; x++)
					if(tileMap[x][y] != 0)
						return false;
			return true;
	}
	
	public boolean isSpawn(int x, int y, Creature c){
		if(c != player && (player.getSpawnX() == x) && (player.getSpawnY() == y))
			return true;
		for(int i = 0; i < ghosts.length; i++){
			if(c != ghosts[i] && (ghosts[i].getSpawnX() == x) && (ghosts[i].getSpawnY() == y)){
				return true;
			}
		}
		return false;
	}

	public Point[] getAllWayTiles(){
		return allWayTiles;
	}
	
	public void changeModes(){
		for (Ghost g : ghosts) {
			g.changeMode();
		}
	}
	
	public void changeShowDestinations(){
		showDestinations = !showDestinations;
		updateDestinations();
	}
	private void updateDestinations(){
		if(showDestinations)
			for(Ghost g : ghosts)
				g.updateDestination(false);
		else{
			for(Ghost g : ghosts)
				g.updateDestination(true);
		}
	}
	
	public boolean showDestinations(){
		return showDestinations;
	}
	
	public boolean coordIsFullWay(int x, int y){
		return tileMap[x][y] == Tile.FULL_WAY || tileMap[x][y] == Tile.FULL_WAY_RED ||
				tileMap[x][y] == Tile.FULL_WAY_BLUE || tileMap[x][y] == Tile.FULL_WAY_PINK;
	}
	
	public void resetSpawns(){
		player.setSpawnX(player.getSpawnX());
		player.setSpawnY(player.getSpawnY());
		player.setDirection(Creature.RIGHT);
		player.resetState();
		
		for(Ghost g : ghosts){
			g.setSpawnX(g.getSpawnX());
			g.setSpawnY(g.getSpawnY());
		}
		resetGhostMode();
		
	}
	
	private void resetGhostMode(){
		for(Ghost g : ghosts){
			g.setCurrentMode(Ghost.MODE_SPREAD);
			g.updateFields();
		}
	}
}
