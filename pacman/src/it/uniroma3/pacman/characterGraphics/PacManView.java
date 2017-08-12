package it.uniroma3.pacman.characterGraphics;

import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.AnimatedView;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import it.uniroma3.resources.ResourceManager;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class PacManView extends AnimatedView implements OnMoveListener {
	private static final Point2D INITIAL_POSITION = new Point2D(256, 400);
	
	private Direction direction;
	
	private boolean stopped;

	/**
	 * Angles of rotating the images.
	 */														
	private static final int[] ROTATION_DEGREE = new int[] {0, 90, 180, 270};

	public PacManView() {
		super();
		
		setPosition(INITIAL_POSITION);
		setDirection(Direction.WEST);
		stopped = true;
		
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
		if (stopped) return;
		setX(getX() + getDirection().getDirX() * MOVE_SPEED);
		setY(getY() + getDirection().getDirY() * MOVE_SPEED);
		
		int nextX = getX() + getDirection().getDeltaX();
		int nextY = getY() + getDirection().getDeltaY();
		
		int nextCollision = SharedMazeData.getDataForPosition(nextX, nextY);
		
		if (nextCollision == SharedMazeData.BLOCK || nextCollision == SharedMazeData.CAGE_BOUNDARY_LIMIT) {
			stopped = true;
			pauseImageAnimation(0);
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
		stopped = false;
		
		playImageAnimation();
		return true;
	}
	

	/**
	 * Place Pac-Man at the startup position for a new game.
	 */
	public void resetStatus() {
		stopped = false;
		this.direction = Direction.WEST;

		setCurrentImageIndex(0);

		setPosition(INITIAL_POSITION);
		
		setVisible(true);
		start();
		playImageAnimation();
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