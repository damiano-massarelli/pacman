package it.uniroma3.pacman.collision.handlers;

import java.util.HashMap;
import java.util.Map;
import it.uniroma3.pacman.collision.CollisionHandler;

/**
 * Permette di collegare altri {@link CollisionHandler}
 * alla collisione di due oggetti dei tipi specificati. Utilizzando il metodo
 * {@link AutomaticCollisionHandler#addCollisionHandler(CollisionHandler handler, Class firstClass, Class secondClass)} si
 * specifica che <b>handler</b> deve essere utilizzato per gestire
 * la collisione tra oggetti di tipo <b>firstClass</b> e <b>secondClass</b>.
 * @author damiano
 *
 */
public class AutomaticCollisionHandler implements CollisionHandler {
	
	private Map<String, CollisionHandler> name2collisionHandler;
	
	public AutomaticCollisionHandler() {
		name2collisionHandler = new HashMap<>();
	}
	
	/**
	 * <b>handler</b> verrà utilizzato per gestire le collisioni tra oggetti di tipo
	 * <b>firstClass</b> e oggetti di tipo <b>secondClass</b>. Il metodo
	 * {@link CollisionHandler#handle} di <b>handler</b> verrà sempre chiamato usando
	 * come primo argomento l'oggetti di tipo <b>firstClass</b> e come secondo
	 * argomento l'oggetto di tipo <b>secondClass</b> 
	 * @param handler il {@link CollisionHandler} per le collisioni tra oggetti dei 
	 * tipi specificati nei seguenti parametri
	 * @param firstCollidable il tipo del primo oggetto
	 * @param secondCollidable il tipo del secondo oggetto
	 */
	public void addCollisionHandler(CollisionHandler handler, Class firstCollidable, Class secondCollidable) {
		String handlerName = firstCollidable.getCanonicalName() + secondCollidable.getCanonicalName();
		this.name2collisionHandler.put(handlerName, handler);
	}
	

	@Override
	public void handle(Object o1, Object o2) {
		String handlerName = o1.getClass().getCanonicalName() + o2.getClass().getCanonicalName();
		String handlerNameReverse = o2.getClass().getCanonicalName() + o2.getClass().getCanonicalName();
		if (name2collisionHandler.containsKey(handlerName))
			name2collisionHandler.get(handlerName).handle(o1, o2);
		else if (name2collisionHandler.containsKey(handlerNameReverse))
			name2collisionHandler.get(handlerNameReverse).handle(o2, o1);
	}

}
