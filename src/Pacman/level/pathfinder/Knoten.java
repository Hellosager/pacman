package Pacman.level.pathfinder;


public class Knoten {

	public static final int KOSTEN_F�R_FELD = 1;
	private int x, y;
	private boolean solid;
	private int geringsteGesamtKosten = 8888;
	private Knoten vorg�nger = null;
	
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
	
	public void setVorg�nger(Knoten vorg�nger){
		this.vorg�nger = vorg�nger;
	}
	
	public Knoten getVorg�nger(){
		return vorg�nger;
	}
}
