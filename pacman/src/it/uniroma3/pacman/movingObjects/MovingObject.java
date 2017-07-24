package it.uniroma3.pacman.movingObjects;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.collision.Collidable;
import it.uniroma3.pacman.graphics.View;
import it.uniroma3.pacman.maze.SharedMazeData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * MovingObject.fx created on 2009-1-1, 11:40:49 <br>
 * MovingObject.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public abstract class MovingObject extends View implements Collidable {
	protected static final int ANIMATION_STEP = 4;
	protected static final int MOVE_SPEED = SharedMazeData.GRID_GAP / ANIMATION_STEP;

	protected static final int MOVING = 1;
	protected static final int STOPPED = 0;

	public static final int MOVE_LEFT = 0;
	public static final int MOVE_UP = 1;
	public static final int MOVE_RIGHT = 2;
	public static final int MOVE_DOWN = 3;

	protected int state;

	private int currentImage;
	private Image[] images;

	private Direction direction;

	private Timeline timeline;
	private int tickElapsed = 0;
	private int tickPerImage = ANIMATION_STEP;
	
	private List<OnMoveListener> moveListeners;

	public MovingObject() {
		this(0, 0, new Direction(0, 0));
	}
	
	public MovingObject(int x, int y, Direction direction) {
		super(x, y);
		moveListeners = new ArrayList<>();
		
		currentImage = 0;

		this.direction = direction;
		timeline = createTimeline();
	}
	
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void setDirection(Direction dir) {
		this.direction = dir;
	}
	
	public void setImages(Image[] images) {
		this.images = images;
		this.currentImage = 0;
	}
	

	public int getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(int currentImage) {
		this.currentImage = currentImage;
	}

	public int getTickPerImage() {
		return tickPerImage;
	}

	public void setTickPerImage(int tickPerImage) {
		this.tickPerImage = tickPerImage;
	}

	public void addOnMoveListener(OnMoveListener listener) {
		this.moveListeners.add(listener);
	}
	
	private void moveOneStep() {
		tickElapsed++;
		if (tickElapsed >= tickPerImage) {
			currentImage = (currentImage + 1) % images.length;
			setImage(images[currentImage]);
		}
			
		for (OnMoveListener l : moveListeners)
			l.onMove();
	}

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
