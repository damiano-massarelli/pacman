package it.uniroma3.pacman.collision;

import javafx.geometry.Point2D;

/** Un oggetto di una classe che implementa Collidable può essere 
 * utilizzato da un {@link CollisionDetector} e può quindi entrare
 * in collisione con altri oggetti aggiunti allo stesso {@link CollisionDetector}
 * @author damiano
 */

public interface Collidable {
//	/** 
//	 * @return la posizione sull'asse x
//	 */
//	public int getX();
//	/** 
//	 * @return la posizione sull'asse x
//	 */
//	public int getY();
	
	public Point2D getPosition();
	
	/** 
	 * @return il raggio dell'oggetto. Se la distanza fra due oggetti è minore
	 * della somma dei loro raggi si scatena una collisione
	 */
	public int getRadius();
	
	
	
}
