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

	/**
	 * Angles of rotating the images.
	 */
	private static final int[] ROTATION_DEGREE = new int[] {0, 90, 180, 270};

	/**
	 * Current direction of Pac-Man.
	 */
	private final SimpleIntegerProperty imageDirection;

	public PacManView() {
		super(START_POSITION_X, START_POSITION_Y, new Direction(-1, 0));
		
		addOnMoveListener(this);

		ResourceManager resMgr = ResourceManager.getInstance();

		Image defaultImage = new Image(resMgr.getResourceAsStream("/images/left1.png"));
		images = new Image[] {defaultImage,
				new Image(resMgr.getResourceAsStream("/images/left2.png")),
				defaultImage,
				new Image(resMgr.getResourceAsStream("/images/round.png"))
		};

		imageDirection = new SimpleIntegerProperty(MOVE_LEFT);

		ImageView pacmanImage = new ImageView(defaultImage);
		pacmanImage.xProperty().bind(this.getXProperty().add(-13));
		pacmanImage.yProperty().bind(this.getYProperty().add(-13));

		pacmanImage.imageProperty().bind(imageBinding);
		IntegerBinding rotationBinding = new IntegerBinding() {

			{
				super.bind(imageDirection);
			}

			@Override
			protected int computeValue() {
				return ROTATION_DEGREE[imageDirection.get()];
			}
		};
		pacmanImage.rotateProperty().bind(rotationBinding);
		
		getChildren().add(pacmanImage); // patweb

	}
	
	
	private void move() {
		//System.out.println(">> " + getX() + " " + getY());
//		setX(getX() + (int)getDirection().getDx() * MOVE_SPEED);
//		setY(getY() + (int)getDirection().getDy() * MOVE_SPEED);
		setLayoutX(getLayoutX() + getDirection().getDeltaX() * MOVE_SPEED);
		setLayoutY(getLayoutY() + getDirection().getDeltaY() * MOVE_SPEED);
		
		int nextX = getX() + SharedMazeData.GRID_GAP * getDirection().getDx();
		int nextY = getY() + SharedMazeData.GRID_GAP * getDirection().getDy();
		
		int nextCollision = SharedMazeData.getDataForPosition(nextX, nextY);
		
		if (nextCollision == SharedMazeData.BLOCK || nextCollision == SharedMazeData.CAGE_BOUNDARY_LIMIT) {
			state = STOPPED;
		}
	}
	

	public boolean attemptToSetDirection(Direction dir) {
		if (this.getDirection().equals(dir))
			return false;
		
		int nextX = (int) (getX() +  dir.getDx() * SharedMazeData.GRID_GAP); 
		int nextY = (int) (getY() + dir.getDy() * SharedMazeData.GRID_GAP);
		
		int nextCollision = SharedMazeData.getDataForPosition(nextX, nextY);
		if (nextCollision == SharedMazeData.BLOCK || nextCollision == SharedMazeData.CAGE_BOUNDARY_LIMIT || nextCollision == SharedMazeData.INVALID_POINT_IN_MAZE)
			return false;
		
		this.setDirection(dir);
		state = MOVING;
		
		return true;
	}
	
	@Override
	public void onMove() {
		// handle keyboard input only when Pac-Man is at a point on the grid
		if (currentImage.get() == 0) {
			//handleKeyboardInput();
		}
		if (state == MOVING) {
			move();
			// switch to the image of the next frame
			if (currentImage.get() < ANIMATION_STEP - 1) 
				currentImage.set(currentImage.get() + 1);
			else
				currentImage.set(0);
		}
	}

	/**
	 * Place Pac-Man at the startup position for a new game.
	 */
	public void resetStatus() {
		state = MOVING;
		imageDirection.set(MOVE_LEFT);

		currentImage.set(0);


		setX(START_POSITION_X);
		setY(START_POSITION_Y);
		
		getDirection().setDxDy(-1, 0);

		setVisible(true); // patweb: Added because Pac-Man is invisible at start of new life.
		start();
	}
	
	

	public void hide() {
		setVisible(false);
		getTimeline().stop();
	}
	
}