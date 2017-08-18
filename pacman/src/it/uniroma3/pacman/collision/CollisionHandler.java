package it.uniroma3.pacman.collision;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.pacman.staticObjects.Teleport;

/**
 * Un oggetto di una classe che implementa questa interfaccia può essere utilizzato
 * come Gestore delle collisioni per un {@link CollisionDetector}
 * @author damiano
 *
 */
public class CollisionHandler {
	/**
	 * handle viene chiamato dal {@link CollisionDetector} a cui questo handler è
	 * stato aggiunto quando si verifica una collisione tra due oggetti gestiti 
	 * dal {@link CollisionDetector}
	 * @param o1 il primo oggetto
	 * @param o2 il secondo oggetto
	 */
	public void handle(PacMan pacMan, Ghost ghost) {
		System.out.println("PacMan - Ghost :)");
	}
	
	public void handle(PacMan pacMan, Dot dot) {
		
	}
	
	public void handle(PacMan pacMan, MagicDot magicDot) {
		
	}
	
	public void handle(PacMan pacMan, Teleport teleport) {
		
	}
	
	public void handle(Ghost ghost, Teleport teleport) {
		
	}

}
