package Pacman.editor.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import Pacman.creatures.Creature;
import Pacman.creatures.Player;
import Pacman.editor.Editor;
import Pacman.editor.SpawnManager;

public class SpawnManagerInput implements ActionListener{
	private SpawnManager sm;
	private JFrame mainframe, spawnFrame;
	private Editor editor;
	
	
	public SpawnManagerInput(SpawnManager sm, JFrame mainframe, JFrame spawnFrame, Editor editor) {
		this.sm = sm;
		this.mainframe = mainframe;
		this.spawnFrame = spawnFrame;
		this.editor = editor;
	}
	
	public void actionPerformed(ActionEvent e) {
		Creature[] creatures = editor.getEditMap().getCreatures();
		Player player = editor.getEditMap().getPlayer();
		
		try{
			int x = new Integer(sm.getPlayerX().getText());
			int y = new Integer(sm.getPlayerY().getText());
			
			if(editor.getEditMap().getTileMap()[x][y] == 0 || editor.getEditMap().isSpawn(x, y, player)){
				player.setSpawnX(0);
				player.setSpawnY(0);
			}else{
				editor.setSaved(false);
				player.setSpawnX(x);
				player.setSpawnY(y);
			}
		}catch(NumberFormatException exception){
			player.setSpawnX(0);
			player.setSpawnY(0);
		}catch(ArrayIndexOutOfBoundsException aioobe){
			player.setSpawnX(0);
			player.setSpawnY(0);
		}
		
		for(int i = 0; i < creatures.length; i++){
			try{
				int x = new Integer(sm.getTextfields()[i][0].getText());
				int y = new Integer(sm.getTextfields()[i][1].getText());

				if(editor.getEditMap().getTileMap()[x][y] == 0 || editor.getEditMap().isSpawn(x, y, creatures[i])){
					creatures[i].setSpawnX(0);
					creatures[i].setSpawnY(0);
				}else{
					editor.setSaved(false);
					creatures[i].setSpawnX(x);
					creatures[i].setSpawnY(y);
				}
			}
			catch(NumberFormatException nfe){
				creatures[i].setSpawnX(0);
				creatures[i].setSpawnY(0);
			}catch(ArrayIndexOutOfBoundsException aioobe){
				creatures[i].setSpawnX(0);
				creatures[i].setSpawnY(0);
			}
		}
			

		
		mainframe.setEnabled(true);
		spawnFrame.dispose();
		mainframe.requestFocus();
	}

}
