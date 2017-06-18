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
	
	private List<CollisionHandler> collisionHandlers;
	
	private List<Collidable> collidables;
	private List<CollisionTrigger> triggers;
	
	/** 
	 * Crea un nuovo {@link CollisionDetector}
	 * @param detectFrequencyMillis ogni quanti millisecondi si controlla per una nuova
	 * collisione
	 * @param handler un {@link CollisionHandler} che gestirà le collisioni
	 */
	public CollisionDetector(double detectFrequencyMillis) {
		collidables = new ArrayList<>();
		triggers = new ArrayList<>();
		this.collisionHandlers = new ArrayList<>();
		
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(detectFrequencyMillis), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				callTriggers();
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
	public CollisionDetector() {
		this(DEFAULT_DETECT_FREQ_MILLIS);
	}
	
	public void callTriggers() {
		for (CollisionTrigger trigger : triggers)
			if (trigger.collisionOccurred())
				handleCollision(trigger.getFirst(), trigger.getSecond());
	}
	
	public void detectCollisions() {
		for (int i = 0; i < collidables.size(); i++) {
			for (int j = i+1; j < collidables.size(); j++) {
				Collidable o1 = collidables.get(i);
				Collidable o2 = collidables.get(j);
//				double distX = o1.getX() - o2.getX();
//				double distY = o1.getY() - o2.getY();
//				double distance = Math.sqrt(distX * distX + distY * distY);
				//System.out.println(distance);
				
				double distance = o1.getPosition().distance(o2.getPosition());
				
				if (distance < o1.getRadius() + o2.getRadius())
					handleCollision(o1, o2);
			}
		}
	}

	private void handleCollision(Object o1, Object o2) {
		for (CollisionHandler handler : collisionHandlers)
			handler.handle(o1, o2);
	}
	
	/**
	 * Aggiunge un nuovo {@link Collidable} alla lista degli oggetti che possono
	 * entrare in collisione
	 * @param collidable l'oggetto da aggiungere
	 */
	public void addCollidable(Collidable collidable) {
		this.collidables.add(collidable);
	}
	
	/**
	 * Aggiunge un nuovo {@link CollisionTrigger} alla lista dei collision triggers
	 * @param trigger il trigger da aggiungere
	 */
	public void addTrigger(CollisionTrigger trigger) {
		this.triggers.add(trigger);
	}
	
	/**
	 * Aggiunge un nuovo {@link CollisionHandler}
	 * @param handler il {@link CollisionHandler} da aggiungere
	 */
	public void addCollisionHandler(CollisionHandler handler) {
		this.collisionHandlers.add(handler);
	}
	
}
