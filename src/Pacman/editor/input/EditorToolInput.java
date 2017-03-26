package Pacman.editor.input;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Pacman.creatures.Creature;
import Pacman.editor.Editor;
import Pacman.editor.SpawnManager;
import Pacman.gfx.Assets;
import Pacman.gui.MainMenu;
import Pacman.tiles.Tile;

public class EditorToolInput implements ActionListener, KeyListener{

	private Editor editor;
	private JPanel tools;
	private JComboBox tileWahl;
	private JButton saveMap;
	private JTextField nameMap;
	private String levelName;
	
	public EditorToolInput(Editor editor) {
		this.editor = editor;
		this.tileWahl = editor.getTileWahl();
		this.saveMap = editor.getSaveMap();
		this.nameMap = editor.getNameMap();
		this.tools = editor.getTools();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Auswahl":
			editor.setCurrentTile(Tile.tileTextures[tileWahl.getSelectedIndex()]);
			break;
		case "Speichern":
			editor.setSaved(true);
			editor.getEditMap().saveLevel(levelName);
			break;
		case "Laden":
			// FUNKTIONIERT IN JAR-DATEI +++
			FileDialog fd = new FileDialog(editor.getDisplay().getFrame(), "Load Level", FileDialog.LOAD);
			fd.setFile("*.txt");
			fd.setDirectory("Files/Level/");
			fd.setVisible(true);
			String path = fd.getFile();
			if(path != null){
				path = "Files/Level/" + path;
				editor.getEditMap().loadLevel(path);
				editor.setTileMap(editor.getEditMap().getTileMap());
				editor.setSaved(true);
			}
			break;
		case "Spawnen":
			createNewSpawnManager();
			break;
		case "Menu":
			if(editor.isSaved()){
				editor.setRunning(false);
				new MainMenu(editor.getDisplay());
			}
			else{
				JOptionPane.setDefaultLocale(Locale.ENGLISH);	// Antworten auf Englisch
				int answer = JOptionPane.showConfirmDialog(editor.getDisplay().getFrame(),	// frame
						"Changes has not been saved yet. Do you want to continue?",		// message
						"Warning!", JOptionPane.YES_NO_OPTION, 0, new ImageIcon(Assets.pacmanDirections[Creature.RIGHT][0]));	// title, Optionen, Keine Ahnung, Image
				switch(answer){
				case 0:	
					editor.setRunning(false);
					new MainMenu(editor.getDisplay()); 
					break;
				default: break;
				}
			}
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		levelName = nameMap.getText();
		editor.getEditMap().setLevelName(levelName);
		if(editor.getEditMap().exists() || levelName.equals(""))
			saveMap.setEnabled(false);
		else{
			saveMap.setEnabled(true);
		}		
	}

	@Override
	public void keyPressed(KeyEvent e) {}


	@Override
	public void keyTyped(KeyEvent e) {}
	
	
	private void createNewSpawnManager(){
		new SpawnManager(editor.getDisplay().getFrame(), editor);
	}
}
