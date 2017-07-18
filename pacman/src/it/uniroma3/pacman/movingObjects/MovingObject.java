package it.uniroma3.pacman.movingObjects;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.collision.Collidable;
import it.uniroma3.pacman.maze.SharedMazeData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * MovingObject.fx created on 2009-1-1, 11:40:49 <br>
 * MovingObject.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public abstract class MovingObject extends Parent implements Collidable {
	//public mixin class MovingObject {

	private static final int DEFAULT_RADIUS = 11;
	
	protected static final int ANIMATION_STEP = 4;
	protected static final int MOVE_SPEED = SharedMazeData.GRID_GAP / ANIMATION_STEP;

	protected static final int MOVING = 1;
	protected static final int STOPPED = 0;

	public static final int MOVE_LEFT = 0;
	public static final int MOVE_UP = 1;
	public static final int MOVE_RIGHT = 2;
	public static final int MOVE_DOWN = 3;

	protected int state;

	protected IntegerProperty currentImage;
	protected Image[] images;
	protected ObjectBinding imageBinding;

	private Direction direction;
	
	private IntegerProperty x, y;
	private int radius;

	private Timeline timeline;
	
	private List<OnMoveListener> moveListeners;

	public MovingObject() {
		this(0, 0, new Direction(0, 0));
	}
	
	public MovingObject(int x, int y, Direction direction) {
		
		moveListeners = new ArrayList<>();
		
		currentImage = new SimpleIntegerProperty(0);

		imageBinding = new ObjectBinding() {

			{
				super.bind(currentImage);
			}

			@Override
			protected Image computeValue() {
				return images[currentImage.get()];
			}
		};

		
		this.direction = direction;
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		
		this.radius = DEFAULT_RADIUS;
		
		timeline = createTimeline();
	}
	
	public int getX() {
		return x.get();
	}
	
	public int getY() {
		return y.get();
	}
	
	public Point2D getPosition() {
		return new Point2D(getX(), getY());
	}
	
	@Override
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void setX(int x) {
		this.x.set(x);
	}
	
	public void setY(int y) {
		this.y.set(y);
	}
	
	public IntegerProperty getXProperty() {
		return this.x;
	}
	
	public IntegerProperty getYProperty() {
		return this.y;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void setDirection(Direction dir) {
		this.direction = dir;
	}
	

	public void addOnMoveListener(OnMoveListener listener) {
		this.moveListeners.add(listener);
	}
	
	private void moveOneStep() {
		for (OnMoveListener l : moveListeners)
			l.onMove();
	}

	// animation time line, moving the pacman

	private Timeline createTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(45), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				moveOneStep();
			}

		});
		timeline.getKeyFrames().add(kf);

		return timeline;
	}
	
	public Timeline getTimeline() {
		return timeline;
	}

	public void stop() {
		timeline.stop();
	}

	public void pause() {
		timeline.pause();
	}

	public void start() {
		timeline.play();
	}

	public boolean isRunning() {
		return timeline.getStatus() == Animation.Status.RUNNING;
	}

	public boolean isPaused() {
		return timeline.getStatus() == Animation.Status.PAUSED;
	}

}
