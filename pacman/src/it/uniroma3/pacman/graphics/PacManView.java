package it.uniroma3.pacman.graphics;

import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.MovingObject;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import it.uniroma3.resources.ResourceManager;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PacManView extends MovingObject implements OnMoveListener {
	public static int START_POSITION_X = 256;
	public static int START_POSITION_Y = 400;
	
	private Direction direction;

	/**
	 * Angles of rotating the images.
	 */														
	private static final int[] ROTATION_DEGREE = new int[] {0, 90, 180, 270};

	public PacManView() {
		super(null, START_POSITION_X, START_POSITION_Y);
		
		setDirection(Direction.WEST);
		
		addOnMoveListener(this);

		ResourceManager resMgr = ResourceManager.getInstance();

		Image defaultImage = new Image(resMgr.getResourceAsStream("/images/left1.png"));
		setImages(new Image[] {defaultImage,
				new Image(resMgr.getResourceAsStream("/images/left2.png")),
				defaultImage,
				new Image(resMgr.getResourceAsStream("/images/round.png"))
		});
	
	}
	
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
		getImageView().setRotate(ROTATION_DEGREE[direction.ordinal()]);
	}

	@Override
	public void onMove() {
		System.out.println("x: " + getX() + " y: " + getY());
		if (state == STOPPED) return;
		setX(getX() + getDirection().getDirX() * MOVE_SPEED);
		setY(getY() + getDirection().getDirY() * MOVE_SPEED);
		
		int nextX = (int )getX() + getDirection().getDeltaX();
		int nextY = (int)getY() + getDirection().getDeltaY();
		
		int nextCollision = SharedMazeData.getDataForPosition(nextX, nextY);
		
		if (nextCollision == SharedMazeData.BLOCK || nextCollision == SharedMazeData.CAGE_BOUNDARY_LIMIT) {
			state = STOPPED;
		}
	}
	

	public boolean attemptToSetDirection(Direction dir) {
		if (this.getDirection().equals(dir))
			return false;
		
		int nextX = (int) (getX() +  dir.getDeltaX()); 
		int nextY = (int) (getY() + dir.getDeltaY());
		
		int nextCollision = SharedMazeData.getDataForPosition(nextX, nextY);
		if (nextCollision == SharedMazeData.BLOCK || nextCollision == SharedMazeData.CAGE_BOUNDARY_LIMIT || nextCollision == SharedMazeData.INVALID_POINT_IN_MAZE)
			return false;
		
		this.setDirection(dir);
		state = MOVING;
		
		return true;
	}
	
//	@Override
//	public void onMove() {
//		// handle keyboard input only when Pac-Man is at a point on the grid
//		if (currentImage.get() == 0) {
//			//handleKeyboardInput();
//		}
//		if (state == MOVING) {
//			move();
//			// switch to the image of the next frame
//			if (currentImage.get() < ANIMATION_STEP - 1) 
//				currentImage.set(currentImage.get() + 1);
//			else
//				currentImage.set(0);
//		}
//	}

	/**
	 * Place Pac-Man at the startup position for a new game.
	 */
	public void resetStatus() {
		state = MOVING;
		this.direction = Direction.WEST;

		setCurrentImageIndex(0);

		setX(START_POSITION_X);
		setY(START_POSITION_Y);

		setVisible(true); // patweb: Added because Pac-Man is invisible at start of new life.
		start();
	}
	
	

	public void hide() {
		setVisible(false);
		getTimeline().stop();
	}


	@Override
	public int getRadius() {
		return 0;
	}
	
}