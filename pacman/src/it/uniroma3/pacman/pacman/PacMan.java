package it.uniroma3.pacman.pacman;

import it.uniroma3.pacman.game.PacmanGame;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.MovingObject;
import it.uniroma3.resources.ResourceManager;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * PacMan.fx created on 2009-1-1, 11:50:58 <br>
 * PacMan.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class PacMan extends MovingObject {
	
	public static int START_POSITION_X = 256;
	public static int START_POSITION_Y = 400;
	
	/**
	 * if score/10'000 is different from lastScoreInTenThausands
	 * add a life to pacman
	 */
	private int lastScoreInTenThousands = 0;
	
	/**
	 * The number of dots eaten.
	 */
	private int dotEatenCount;
	
	/**
	 * PacMan lives
	 */
	
	private SimpleIntegerProperty lives;

	/**
	 * Score of the game.
	 */
	private SimpleIntegerProperty score;

	/**
	 * Angles of rotating the images.
	 */
	private static final int[] ROTATION_DEGREE = new int[] {0, 90, 180, 270};


	/**
	 * Buffer to keep the keyboard input.
	 */
	private int keyboardBuffer;

	/**
	 * Current direction of Pac-Man.
	 */
	private final SimpleIntegerProperty imageDirection;

	/**
	 * Constructor.
	 *
	 * @param x
	 * @param y
	 */
	public PacMan() {
		super(START_POSITION_X, START_POSITION_Y, new Direction(-1, 0));
		
		ResourceManager resMgr = ResourceManager.getInstance();
		
		Image defaultImage = new Image(resMgr.getResourceAsStream("/images/left1.png"));
		images = new Image[] {defaultImage,
				new Image(resMgr.getResourceAsStream("/images/left2.png")),
				defaultImage,
				new Image(resMgr.getResourceAsStream("/images/round.png"))
		};

		dotEatenCount = 0;
		score = new SimpleIntegerProperty(0);   
		lives = new SimpleIntegerProperty(3);
		
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

		keyboardBuffer = -1;
		

		getChildren().add(pacmanImage); // patweb
	}
	
	public int getDotEatenCount() {
		return dotEatenCount;
	}

	public void setDotEatenCount(int dotEatenCount) {
		this.dotEatenCount = dotEatenCount;
	}

	public int getScore() {
		return score.get();
	}

	public void setScore(int score) {
		this.score.set(score);
		if (getScore()/10000 != lastScoreInTenThousands) {
			setLives(getLives() + 1);
			lastScoreInTenThousands = getScore()/10000;
		}
	}
	
	public SimpleIntegerProperty getScoreProperty() {
		return this.score;
	}
	
	public int getLives() {
		return lives.get();
	}
	
	public void setLives(int lives) {
		this.lives.set(lives);
	}
	
	public SimpleIntegerProperty getLivesProperty() {
		return lives;
	}

	private void move() {
		//System.out.println(">> " + getX() + " " + getY());
		setX(getX() + (int)getDirection().getDx() * MOVE_SPEED);
		setY(getY() + (int)getDirection().getDy() * MOVE_SPEED);
		
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
		
		boolean notOutOfBoundsX = nextX >= 0 && nextX < SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP;
		boolean notOutOfBuondsY = nextY >= 0 && nextY < SharedMazeData.getGridHeight() * SharedMazeData.GRID_GAP;
		System.out.println("nx " + nextX +" x " + notOutOfBoundsX + " y " + notOutOfBuondsY + 
				" %x" + (nextX % SharedMazeData.GRID_GAP == 0) + " %y " +  (nextY % SharedMazeData.GRID_GAP == 0));
		
		
		int nextCollision = SharedMazeData.getDataForPosition(nextX, nextY);
		System.out.println(nextCollision);
		if (nextCollision == SharedMazeData.BLOCK || nextCollision == SharedMazeData.CAGE_BOUNDARY_LIMIT || nextCollision == SharedMazeData.INVALID_POINT_IN_MAZE)
			return false;
		
		this.setDirection(dir);
		state = MOVING;
		
		return true;
	}

	/**
	 * Handle keyboard input.
	 */
	private void handleKeyboardInput() {

		if (keyboardBuffer < 0) {
			return;
		}
		Direction newDirection = null;
		if (keyboardBuffer == MOVE_LEFT)
			newDirection = new Direction(-1, 0);
		else if (keyboardBuffer == MOVE_RIGHT)
			newDirection = new Direction(1, 0);
		else if (keyboardBuffer == MOVE_UP)
			newDirection = new Direction(0, -1);
		else if (keyboardBuffer == MOVE_DOWN)
			newDirection = new Direction(0, 1);
		
		if (attemptToSetDirection(newDirection) == true)
			imageDirection.set(keyboardBuffer); // TODO: da cambiare se cambia handleKeyboardInput

	}


	public void setKeyboardBuffer(int k) {
		keyboardBuffer = k;
	}

	

	public void hide() {
		setVisible(false);
		getTimeline().stop();
	}

	/**
	 * Handle animation of one tick.
	 */
	@Override
	public void moveOneStep() {
		// handle keyboard input only when Pac-Man is at a point on the grid
		if (currentImage.get() == 0) {
			handleKeyboardInput();
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

		keyboardBuffer = -1;
		currentImage.set(0);


		setX(START_POSITION_X);
		setY(START_POSITION_Y);
		
		getDirection().setDxDy(-1, 0);

		setVisible(true); // patweb: Added because Pac-Man is invisible at start of new life.
		start();
	}
	
	public void die(PacmanGame game) {
		hide();
		DyingPacMan dyingPacMan = new DyingPacMan(game);
		dyingPacMan.startAnimation(getX(), getY());
	}

}
