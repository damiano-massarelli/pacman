package it.uniroma3.pacman.collision;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


/**
 * Un CollisionDetectorn permette di individuare collisioni fra oggetti di tipo
 * {@link Collidable} o tra oggetti di qualsiasi tipo attraverso i {@link CollisionTrigger}.
 * 
 * Due oggetti {@link Collidable} entrano in collisione se la loro distanza è minore
 * della somma dei loro raggi. In questo caso i due oggetti vengono passati ai 
 * collisionHandlers che si occuperanno di gestire questa collisione.
 * 
 * Una collisione può essere scatenata anche da un {@link CollisionTrigger}. La gestione
 * avviene allo stesso modo, ossia i due oggetti in collisione vengono passati ai
 * collisionHandler.
 * 
 * @see CollisionHandler
 * @author damiano
 *
 */
public class CollisionDetector {
	/* A little bit faster than Ghost and Pacman */
	private static final double DEFAULT_DETECT_FREQ_MILLIS = 30;
	
	private Timeline timeline;
	
	private CollisionHandler collisionHandler;
	
	private List<CollidableModelEntity> collidables;
	
	
	/** 
	 * Crea un nuovo {@link CollisionDetector}
	 * @param detectFrequencyMillis ogni quanti millisecondi si controlla per una nuova
	 * collisione
	 * @param handler un {@link CollisionHandler} che gestirà le collisioni
	 */
	public CollisionDetector(CollisionHandler handler, double detectFrequencyMillis) {
		collidables = new ArrayList<>();
		this.collisionHandler = handler;
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(detectFrequencyMillis), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				detectCollisions();
			}
		});
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}
	/** 
	 * Crea un nuovo {@link CollisionDetector}
	 * @param handler un {@link CollisionHandler} che gestirà le collisioni
	 */
	public CollisionDetector(CollisionHandler handler) {
		this(handler, DEFAULT_DETECT_FREQ_MILLIS);
	}
	
	
	public void detectCollisions() {
		//System.out.println("detecting..");
		for (int i = 0; i < collidables.size(); i++) {
			for (int j = i+1; j < collidables.size(); j++) {
				CollidableModelEntity o1 = collidables.get(i);
				CollidableModelEntity o2 = collidables.get(j);
				
				double distance = o1.getSprite().getPosition().distance(o2.getSprite().getPosition());
				
				if (distance < o1.getSprite().getCollisionRadius() + o2.getSprite().getCollisionRadius()) {
					// System.out.println("collided " + o1.getClass() + " " + o2.getClass());
					o1.accept(collisionHandler, o2);
				}
			}
		}
	}

	
	/**
	 * Aggiunge un nuovo {@link Collidable} alla lista degli oggetti che possono
	 * entrare in collisione
	 * @param collidable l'oggetto da aggiungere
	 */
	public void addCollidable(CollidableModelEntity collidable) {
		this.collidables.add(collidable);
	}
	
	public void addCollidables(List<CollidableModelEntity> collidables) {
		this.collidables.addAll(collidables);
	}

	public void stopDetecting() {
		this.timeline.pause();
	}
	
	public void startDetecting() {
		this.timeline.play();
	}
	
}
