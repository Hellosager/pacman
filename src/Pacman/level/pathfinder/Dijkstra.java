package Pacman.level.pathfinder;

import java.awt.Point;
import java.util.ArrayList;

import Pacman.level.Level;

public class Dijkstra {
	
	//Array f�r die Nachbarn eines Knotens
	private static final Point[] POINTS_F�R_NACHBARN = new Point[]{new Point(0,-1), new Point(1, 0), new Point(0, 1), new Point( -1,0)};	
	
	private Knoten[][] tileMapAlsKnoten;
	private ArrayList<Knoten> gepr�fteKnoten = new ArrayList<Knoten>();
	private ArrayList<Knoten> ungepr�fteKnoten = new ArrayList<Knoten>();
	
	public Dijkstra(Level level) {	// Konstruktor
		int widht = level.getWidth();	// breite = die breite die level hat bzw tilemap
		int height = level.getHeight();	// h�he auch
		tileMapAlsKnoten = new Knoten[widht][height];	// KnotenArray genau so gro� wie Tilemap
		for(int x = 0; x < widht; x++){	// Kopierschleifen f�r Array, initialsiert alle Knoten
			for(int y = 0; y < height; y++){
				tileMapAlsKnoten[x][y] = new Knoten(x, y, level.getTile(x, y).isSolid());	// �bergib Koordinaten und ob solid
			}
		}
	}
	
	public void findPath(int ursprungX, int ursprungY, int zielX, int zielY){
		tileMapAlsKnoten[ursprungX][ursprungY].setGeringsteGesamtKosten(0);;	// ursprungsknoten geringsteKosten auf 0, weil ursprung
		tileMapAlsKnoten[ursprungX][ursprungY].setVorg�nger(tileMapAlsKnoten[ursprungX][ursprungY]);
		ungepr�fteKnoten.add(tileMapAlsKnoten[ursprungX][ursprungY]);	// ungepr�ft adden
		// Knoten sind durch Konstruktor alle initalisiert
		
		
		while(!ungepr�fteKnoten.isEmpty()){	// solange es ungepr�fte Knoten gibt
			Knoten currentPr�fKnoten = getKnotenWithLowestSummaryCosts();	// pr�fe den knoten mit geringsten Gesamt kosten
			ungepr�fteKnoten.remove(currentPr�fKnoten);	// entferne diesen aus den ungepr�ften knoten
			gepr�fteKnoten.add(currentPr�fKnoten);	// mache ihn zum gepr�ften knoten
			pr�feNachbarn(currentPr�fKnoten);	// schau dir die nachbarknoten an
		}
	}
	
	
	private Knoten getKnotenWithLowestSummaryCosts(){
		Knoten knotenWithLowestDistance = ungepr�fteKnoten.get(0);	// Fang vorne an
		for (Knoten knoten : ungepr�fteKnoten){	// durchlaufe alle ungepr�ften knoten
			if(knoten.getGeringsteGesamtKosten() < knotenWithLowestDistance.getGeringsteGesamtKosten())	// schau ob der jetztige knoten geringere Gesamtkosten hat
				knotenWithLowestDistance = knoten;	// wenn ja mach ihn zum knoten mit Geringsten gesamtkosten
		}
		return knotenWithLowestDistance;
	}
	

	private void pr�feNachbarn(Knoten currentPr�fKnoten){
		// Durchlaufe alle nachbarn

		for(Point p : POINTS_F�R_NACHBARN){	// wenn Nachbar noch nicht in gepr�ften Knoten
			int x = currentPr�fKnoten.getX() + (int)p.getX();
			int y = currentPr�fKnoten.getY() + (int)p.getY();
			Knoten currentNachbarKnoten = tileMapAlsKnoten[x][y];
			
			// Wenn nachbarKnoten nicht solid, noch nicht in Warteschlange oder schon gepr�ft wurde
			if((!currentNachbarKnoten.isSolid()) && (!ungepr�fteKnoten.contains(currentNachbarKnoten)) && (!gepr�fteKnoten.contains(currentNachbarKnoten))){
				if((currentPr�fKnoten.getGeringsteGesamtKosten() + Knoten.KOSTEN_F�R_FELD) < currentNachbarKnoten.getGeringsteGesamtKosten()){
					// Wenn von pr�fknoten auf nachbar k�rzerer weg gefunden wurde
					// dann setze geringsteKosten neu und �ber welchen knoten diese erreicht wurden
					currentNachbarKnoten.setGeringsteGesamtKosten((currentPr�fKnoten.getGeringsteGesamtKosten() + Knoten.KOSTEN_F�R_FELD));	// +1
					currentNachbarKnoten.setVorg�nger(currentPr�fKnoten);
					// und adde nachbar knoten in queue (offene liste)
					ungepr�fteKnoten.add(currentNachbarKnoten);
				}	// if
			}	// if						
		}	// for
	}	// pr�feNachbarn
	
	public boolean allNodesAreReachable(){
		for(int x = 0; x < tileMapAlsKnoten.length; x++){	// Durchlaufe tileMapAlsKnoten komplett
			for(int y = 0; y < tileMapAlsKnoten[x].length; y++){
				if((!tileMapAlsKnoten[x][y].isSolid()) && (tileMapAlsKnoten[x][y].getVorg�nger() == null)){	// false wenn wegtile keinen vorg�nger
					return false;
				}
			}
		}
	return true;
	}
	
	public void setDefaultKnotenMap(){
		int widht = 24;	// breite = die breite die level hat bzw tilemap
		int height = 24;	// h�he auch
		Knoten[][] tileMapAlsKnoten = new Knoten[widht][height];	// KnotenArray genau so gro� wie Tilemap
		for(int x = 0; x < widht; x++){	// Kopierschleifen f�r Array, initialsiert alle Knoten
			for(int y = 0; y < height; y++){
				if(x == 0 || x == 23 || y == 0 || y == 23)
					tileMapAlsKnoten[x][y] = new Knoten(x, y, true);	// �bergib Koordinaten und ob solid
				else
					tileMapAlsKnoten[x][y] = new Knoten(x, y, false);	// �bergib Koordinaten und ob solid
			}
		}
		
		this.tileMapAlsKnoten = tileMapAlsKnoten;
	}
	
	public Knoten[][] getTileMapAlsKnoten(){
		return tileMapAlsKnoten;
	}
	
}	// class
