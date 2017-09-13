package it.uniroma3.pacman.collision;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


/**
 * A CollisionDetector is responsible for detecting collisions among objects implementing
 * the {@link CollidableModelEntity} interface added to it. Every collision is handled by 
 * the {@link CollisionHandler} passed to the constructor. 
 *
 *	@author damiano
 */
public class CollisionDetector {
	/* A little bit faster than Ghost and Pacman */
	private static final double DEFAULT_DETECT_FREQ_MILLIS = 30;
	
	private Timeline timeline;
	
	private CollisionHandler collisionHandler;
	
	private List<CollidableModelEntity> collidables;
	
	
	/**
	 * Creates a new CollisionDetector
	 * @param handler a {@link CollisionHandler} to handle detected collisions
	 * @param detectFrequencyMillis how often collisions are detected (in milliseconds)
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
	 * Creates a new CollisionDetector
	 * @param handler a {@link CollisionHandler} to handle detected collisions
	 */
	public CollisionDetector(CollisionHandler handler) {
		this(handler, DEFAULT_DETECT_FREQ_MILLIS);
	}
	
	
	private void detectCollisions() {
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
