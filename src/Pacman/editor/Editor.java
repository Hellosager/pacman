package Pacman.editor;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Pacman.editor.input.EditorMouseInput;
import Pacman.editor.input.EditorToolInput;
import Pacman.gui.Display;
import Pacman.level.Level;
import Pacman.tiles.Tile;

public class Editor implements Runnable{

	private int width, height;
	private Display display;
	private boolean running = false;
	private Thread thread;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	private Tile currentTile = Tile.tileTextures[0];
	private EditorMouseInput editMouse;
	private Level editMap;
	private int[][] tileMap;
	
	private String[] tileNamen = new String[] {	"Wall", "Full Way", "Empty Way"	};
	private JPanel tools;
	private JComboBox tileWahl;
	private JButton saveMap, loadMap, manageSpawns, menu;
	private JTextField nameMap;
	
	private boolean saved = true;
	
	public Editor(Display display) {
		this.display = display;
		editMap = new Level("level/tileMap.txt");
		tileMap = editMap.getTileMap();
	}
	
	@Override
	public void run() {
		init();
		
		while(running){
//			tick();
			render();
		}
		
		stop();
		
	}

	private void init() {
		width = display.getWidth();
		height = display.getHeight();	
		display.getFrame().getContentPane().removeAll();
		display.getFrame().getContentPane().setBackground(null);
		display.getFrame().setLayout(new BorderLayout());
		display.getFrame().setLocation(display.getFrame().getX(), display.getFrame().getY()-30);
		
		
		tools = new JPanel();
		tools.setLayout(new FlowLayout());
		
		menu = new JButton("<<<");
		menu.setActionCommand("Menu");
		loadMap = new JButton("Load Level");
		loadMap.setActionCommand("Laden");
		tileWahl = new JComboBox(tileNamen);
		tileWahl.setActionCommand("Auswahl");
		manageSpawns = new JButton("Spawns");
		manageSpawns.setActionCommand("Spawnen");
		saveMap = new JButton("Save Level");
		saveMap.setActionCommand("Speichern");
		saveMap.setEnabled(false);
		nameMap = new JTextField(12);
		nameMap.setActionCommand("Name");
		
		EditorToolInput toolInput = new EditorToolInput(this);
		menu.addActionListener(toolInput);
		loadMap.addActionListener(toolInput);
		tileWahl.addActionListener(toolInput);
		manageSpawns.addActionListener(toolInput);
		saveMap.addActionListener(toolInput);
		nameMap.addKeyListener(toolInput);
		
		tools.add(menu);
		tools.add(loadMap);
		tools.add(tileWahl);
		tools.add(manageSpawns);
		tools.add(saveMap);
		tools.add(nameMap);
				
		
		canvas = new Canvas();
		Dimension dim = new Dimension(width, height);
		canvas.setPreferredSize(dim);
		canvas.setMaximumSize(dim);
		canvas.setMinimumSize(dim);
		canvas.setFocusable(false);
		
		editMouse = new EditorMouseInput(display, this);
		canvas.addMouseListener(editMouse);
		canvas.addMouseMotionListener(editMouse);
		
		display.getFrame().add(tools, BorderLayout.NORTH);
		display.getFrame().add(canvas);


		display.getFrame().pack();
		display.getFrame().repaint();
		display.getFrame().revalidate();
	}

	private void tick(){
		
	}
	
	private void render(){
		bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(3);
			return;
		}
		try{
			g = bs.getDrawGraphics();
		
			g.clearRect(0, 0, width, height);
			// Draw
			// Bei 600x 600 : xMax = 550, yMax = 575
			editMap.render(g);
			currentTile.render(g, editMouse.getTileX(), editMouse.getTileY());
			if(tileWahl.isValid())
			tileWahl.revalidate();
			
			
			bs.show();
			g.dispose();
		}catch(NullPointerException npe){}
	}
	
	public synchronized void start(){
		if(running == true)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(running == false)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int[][] getTileMap(){
		return tileMap;
	}
	
	public void setTileMap(int[][] tileMap){
		this.tileMap = tileMap;
		editMouse.setTileMap(tileMap);
	}
	
	public Tile getCurrentTile(){
		return currentTile;
	}
	
	public void setCurrentTile(Tile tile){
		currentTile = tile;
	}
	
	public JComboBox getTileWahl(){
		return tileWahl;
	}
	
	public JButton getSaveMap(){
		return saveMap;
	}
	
	public Level getEditMap(){
		return editMap;
	}
	
	public JTextField getNameMap(){
		return nameMap;
	}
	
	public JPanel getTools(){
		return tools;
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public boolean isSaved(){
		return saved;
	}
	
	public void setSaved(boolean saved){
		this.saved = saved;
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
}
