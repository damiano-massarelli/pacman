package it.uniroma3.pacman.collision;
/**
 * Un oggetto di una classe che implementa questa interfaccia può essere utilizzato
 * come Gestore delle collisioni per un {@link CollisionDetector}
 * @author damiano
 *
 */
public interface CollisionHandler {
	/**
	 * handle viene chiamato dal {@link CollisionDetector} a cui questo handler è
	 * stato aggiunto quando si verifica una collisione tra due oggetti gestiti 
	 * dal {@link CollisionDetector}
	 * @param o1 il primo oggetto
	 * @param o2 il secondo oggetto
	 */
	public void handle(Object o1, Object o2);
}
