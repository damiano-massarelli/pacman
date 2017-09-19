package it.uniroma3.pacman.movingObjects;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.graphics.Sprite;
import it.uniroma3.pacman.maze.MazeConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * MovingObject.fx created on 2009-1-1, 11:40:49 <br>
 * MovingObject.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class AnimatedSprite extends Sprite {
	protected static final int ANIMATION_STEP = 4;
	protected static final int MOVE_SPEED = MazeConstants.GRID_SIZE / ANIMATION_STEP;


	private int currentImageIndex;
	private Image[] images;
	private ImageView imageView;


	private Timeline timeline;
	private int tickElapsed = 0;
	private int tickPerImage = ANIMATION_STEP;
	private boolean paused = false;
	
	private List<OnMoveListener> moveListeners;

	public AnimatedSprite() {
		this(null, 0, 0);
	}
	
	public AnimatedSprite(Image[] images, int x, int y) {
		super(x, y);
		moveListeners = new ArrayList<>();
		
		currentImageIndex = 0;
		
		this.images = images;
		imageView = new ImageView();
		

		timeline = createTimeline();
		getChildren().add(imageView);
	}
	
		
	public ImageView getImageView() {
		return imageView;
	}


	public void setImages(Image[] images) {
		if (images == null || images.length == 0) throw new IllegalArgumentException("images can be neither null nor empty");
		this.images = images;
		this.currentImageIndex = 0;
		imageView.setImage(images[0]);
		bindImageViewCenterPositionToViewXY();
	}
	
	public void bindImageViewCenterPositionToViewXY() {
		imageView.xProperty().bind(getxProperty().subtract( (imageView.getImage().widthProperty()).divide(2))  ) ;
		imageView.yProperty().bind(getyProperty().subtract( (imageView.getImage().heightProperty()).divide(2))  ) ;
	}

	public int getCurrentImageIndex() {
		return currentImageIndex;
	}

	public void setCurrentImageIndex(int currentImage) {
		this.currentImageIndex = currentImage;
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
		if (tickElapsed >= tickPerImage && images != null && !paused) {
			currentImageIndex = (currentImageIndex + 1) % images.length;
			imageView.setImage(images[currentImageIndex]);
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

	public void pauseImageAnimation(int imageIndex) {
		pauseImageAnimation();
		setCurrentImageIndex(imageIndex);
	}
	
	public void pauseImageAnimation() {
		paused = true;
	}
	
	public void playImageAnimation(int imageIndex) {
		playImageAnimation();
		setCurrentImageIndex(imageIndex);
	}
	
	public void playImageAnimation() {
		paused = false;
	}
	
	public void stop() {
		this.timeline.stop();
	}
	
	public void start() {
		this.timeline.play();
	}


}
