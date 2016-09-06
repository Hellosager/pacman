package Pacman.tiles;

import Pacman.gfx.Assets;

public class WallTile extends Tile{

	public WallTile(int id) {
		super(Assets.wall, id);
		setSolid(true);
	}
	
}
