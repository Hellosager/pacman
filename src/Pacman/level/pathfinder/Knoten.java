package Pacman.level.pathfinder;


public class Knoten {

	public static final int KOSTEN_FÜR_FELD = 1;
	private int x, y;
	private boolean solid;
	private int geringsteGesamtKosten = 8888;
	private Knoten vorgänger = null;
	
	public Knoten(int x, int y, boolean solid) {
		this.x = x;
		this.y = y;
		this.solid = solid;
	}
	
	public void setGeringsteGesamtKosten(int geringsteKosten){
		this.geringsteGesamtKosten = geringsteKosten;
	}

	public int getGeringsteGesamtKosten(){
		return geringsteGesamtKosten;
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public void setVorgänger(Knoten vorgänger){
		this.vorgänger = vorgänger;
	}
	
	public Knoten getVorgänger(){
		return vorgänger;
	}
}
