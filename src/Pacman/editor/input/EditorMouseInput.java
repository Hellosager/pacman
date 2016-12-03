package Pacman.editor.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Pacman.creatures.Creature;
import Pacman.creatures.Player;
import Pacman.editor.Editor;
import Pacman.gui.Display;
import Pacman.tiles.Tile;

public class EditorMouseInput implements MouseListener, MouseMotionListener{
	
	private int mouseX, mouseY;
	private int tileX, tileY;
	private Editor editor;
	private Display display;
	private int[][] tileMap;
	private boolean leftButtonsIsPressed = false;
	
	public EditorMouseInput(Display display, Editor editor) {
		this.display = display;
		this.editor = editor;
		tileMap = editor.getTileMap();
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if(mouseX >= 0 && mouseX <= display.getWidth() && mouseY >= 0 && mouseY <= (display.getHeight()-1)){
		updateTilePosition();
		
			if(leftButtonsIsPressed && !isEdge()){	// Wenn linker Button pressed und nicht auf Kante
				editor.setSaved(false);
				if(editor.getCurrentTile().getID() != 0)	// und wenn nicht Wall ausgewählt
					tileMap[tileX/Tile.TILEWIDTH][tileY/Tile.TILEHEIGHT] = editor.getCurrentTile().getID();
				else if(isNoSpawn())	// ansonsten prüfe vorher ob da ein Spawnpunkt ist
					tileMap[tileX/Tile.TILEWIDTH][tileY/Tile.TILEHEIGHT] = editor.getCurrentTile().getID();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		updateTilePosition();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if((e.getButton() == MouseEvent.BUTTON1 && (!isEdge()))){
			editor.setSaved(false);
			if(editor.getCurrentTile().getID() != 0)
				tileMap[tileX/Tile.TILEWIDTH][tileY/Tile.TILEHEIGHT] = editor.getCurrentTile().getID();
			else if(isNoSpawn())
				tileMap[tileX/Tile.TILEWIDTH][tileY/Tile.TILEHEIGHT] = editor.getCurrentTile().getID();			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftButtonsIsPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftButtonsIsPressed = false;
	}
	
	public int getTileX(){
		return tileX;
	}
	
	public int getTileY(){
		return tileY;
	}

	private void updateTilePosition(){
		tileX = mouseX - mouseX % Tile.TILEWIDTH;
		tileY = mouseY - mouseY % Tile.TILEHEIGHT;
	}
	
	private boolean isEdge(){
		if(		tileX == 0 || tileY == 0 || (tileX == (editor.getEditMap().getWidth()-1)*Tile.TILEWIDTH) ||
				(tileY == (editor.getEditMap().getHeight()-1)*Tile.TILEHEIGHT))
			return true;	
		return false;
	}
	
	private boolean isNoSpawn(){
		Creature[] creatures = editor.getEditMap().getGhosts();
		Player player = editor.getEditMap().getPlayer();
		if((player.getSpawnX() == tileX/Tile.TILEWIDTH) && (player.getSpawnY() == tileY/Tile.TILEHEIGHT))
			return false;
		for(int i = 0; i < creatures.length; i++){
			if((creatures[i].getSpawnX() == tileX/Tile.TILEWIDTH) && (creatures[i].getSpawnY() == tileY/Tile.TILEHEIGHT)){
				return false;
			}
		}
		return true;
	}
	
	public void setTileMap(int[][] tileMap){
		this.tileMap = tileMap;
	}
	
}
