package Pacman.tiles;

import Pacman.gfx.Assets;

public class FullWayTile extends Tile{

	public FullWayTile(int id) {
		super(Assets.fullWay, id);
		setSolid(false);
	}
	
}
