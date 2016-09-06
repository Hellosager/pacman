package Pacman.tiles;

import Pacman.gfx.Assets;

public class EmptyWayTile extends Tile{

	public EmptyWayTile(int id) {
		super(Assets.emptyWay, id);
		setSolid(false);
	}
	
}
