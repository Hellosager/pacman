package Pacman.level.pathfinder;

import java.awt.Point;
import java.util.ArrayList;

import Pacman.level.Level;

public class Dijkstra {
	
	//Array für die Nachbarn eines Knotens
	private static final Point[] POINTS_FÜR_NACHBARN = new Point[]{new Point(0,-1), new Point(1, 0), new Point(0, 1), new Point( -1,0)};	
	
	private Knoten[][] tileMapAlsKnoten;
	private ArrayList<Knoten> geprüfteKnoten = new ArrayList<Knoten>();
	private ArrayList<Knoten> ungeprüfteKnoten = new ArrayList<Knoten>();
	
	public Dijkstra(Level level) {	// Konstruktor
		int widht = level.getWidth();	// breite = die breite die level hat bzw tilemap
		int height = level.getHeight();	// höhe auch
		tileMapAlsKnoten = new Knoten[widht][height];	// KnotenArray genau so groß wie Tilemap
		for(int x = 0; x < widht; x++){	// Kopierschleifen für Array, initialsiert alle Knoten
			for(int y = 0; y < height; y++){
				tileMapAlsKnoten[x][y] = new Knoten(x, y, level.getTile(x, y).isSolid());	// Übergib Koordinaten und ob solid
			}
		}
	}
	
	public void findPath(int ursprungX, int ursprungY, int zielX, int zielY){
		tileMapAlsKnoten[ursprungX][ursprungY].setGeringsteGesamtKosten(0);;	// ursprungsknoten geringsteKosten auf 0, weil ursprung
		tileMapAlsKnoten[ursprungX][ursprungY].setVorgänger(tileMapAlsKnoten[ursprungX][ursprungY]);
		ungeprüfteKnoten.add(tileMapAlsKnoten[ursprungX][ursprungY]);	// ungeprüft adden
		// Knoten sind durch Konstruktor alle initalisiert
		
		
		while(!ungeprüfteKnoten.isEmpty()){	// solange es ungeprüfte Knoten gibt
			Knoten currentPrüfKnoten = getKnotenWithLowestSummaryCosts();	// prüfe den knoten mit geringsten Gesamt kosten
			ungeprüfteKnoten.remove(currentPrüfKnoten);	// entferne diesen aus den ungeprüften knoten
			geprüfteKnoten.add(currentPrüfKnoten);	// mache ihn zum geprüften knoten
			prüfeNachbarn(currentPrüfKnoten);	// schau dir die nachbarknoten an
		}
	}
	
	
	private Knoten getKnotenWithLowestSummaryCosts(){
		Knoten knotenWithLowestDistance = ungeprüfteKnoten.get(0);	// Fang vorne an
		for (Knoten knoten : ungeprüfteKnoten){	// durchlaufe alle ungeprüften knoten
			if(knoten.getGeringsteGesamtKosten() < knotenWithLowestDistance.getGeringsteGesamtKosten())	// schau ob der jetztige knoten geringere Gesamtkosten hat
				knotenWithLowestDistance = knoten;	// wenn ja mach ihn zum knoten mit Geringsten gesamtkosten
		}
		return knotenWithLowestDistance;
	}
	

	private void prüfeNachbarn(Knoten currentPrüfKnoten){
		// Durchlaufe alle nachbarn

		for(Point p : POINTS_FÜR_NACHBARN){	// wenn Nachbar noch nicht in geprüften Knoten
			int x = currentPrüfKnoten.getX() + (int)p.getX();
			int y = currentPrüfKnoten.getY() + (int)p.getY();
			Knoten currentNachbarKnoten = tileMapAlsKnoten[x][y];
			
			// Wenn nachbarKnoten nicht solid, noch nicht in Warteschlange oder schon geprüft wurde
			if((!currentNachbarKnoten.isSolid()) && (!ungeprüfteKnoten.contains(currentNachbarKnoten)) && (!geprüfteKnoten.contains(currentNachbarKnoten))){
				if((currentPrüfKnoten.getGeringsteGesamtKosten() + Knoten.KOSTEN_FÜR_FELD) < currentNachbarKnoten.getGeringsteGesamtKosten()){
					// Wenn von prüfknoten auf nachbar kürzerer weg gefunden wurde
					// dann setze geringsteKosten neu und über welchen knoten diese erreicht wurden
					currentNachbarKnoten.setGeringsteGesamtKosten((currentPrüfKnoten.getGeringsteGesamtKosten() + Knoten.KOSTEN_FÜR_FELD));	// +1
					currentNachbarKnoten.setVorgänger(currentPrüfKnoten);
					// und adde nachbar knoten in queue (offene liste)
					ungeprüfteKnoten.add(currentNachbarKnoten);
				}	// if
			}	// if						
		}	// for
	}	// prüfeNachbarn
	
	public boolean allNodesAreReachable(){
		for(int x = 0; x < tileMapAlsKnoten.length; x++){	// Durchlaufe tileMapAlsKnoten komplett
			for(int y = 0; y < tileMapAlsKnoten[x].length; y++){
				if((!tileMapAlsKnoten[x][y].isSolid()) && (tileMapAlsKnoten[x][y].getVorgänger() == null)){	// false wenn wegtile keinen vorgänger
					return false;
				}
			}
		}
	return true;
	}
	
	public void setDefaultKnotenMap(){
		int widht = 24;	// breite = die breite die level hat bzw tilemap
		int height = 24;	// höhe auch
		Knoten[][] tileMapAlsKnoten = new Knoten[widht][height];	// KnotenArray genau so groß wie Tilemap
		for(int x = 0; x < widht; x++){	// Kopierschleifen für Array, initialsiert alle Knoten
			for(int y = 0; y < height; y++){
				if(x == 0 || x == 23 || y == 0 || y == 23)
					tileMapAlsKnoten[x][y] = new Knoten(x, y, true);	// Übergib Koordinaten und ob solid
				else
					tileMapAlsKnoten[x][y] = new Knoten(x, y, false);	// Übergib Koordinaten und ob solid
			}
		}
		
		this.tileMapAlsKnoten = tileMapAlsKnoten;
	}
	
	public Knoten[][] getTileMapAlsKnoten(){
		return tileMapAlsKnoten;
	}
	
}	// class
