package it.uniroma3.pacman.graphics;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.resources.ResourceManager;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.MovingObject;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Ghost.fx created on 2009-1-28, 14:26:09 <br>
 * Ghost.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class GhostView extends MovingObject implements OnMoveListener{
	
	private static final int STAY_IN_CAGE_DEFAULT_MOVES = 120;

	// the images of animation
	private final Image[] defaultImages;
	
	private final Image[] hollowImages;
	
	private final Image[] flashHollowImages;

	// initial direction and position of a ghost, used in status reset
	private  int initialLocationX;
	private final int initialLocationY;
	private Direction initialDirection;


	// the flag is set if a ghost becomes hollow
	private boolean frightened; // = false;
	
	private int stayInCageMovesLimit;
	
	private OnTurnListener turnListener;

	// the GUI of a ghost
	@SuppressWarnings("unchecked")
	public GhostView(String name, int x, int y) {
		super(x, y, new Direction(1, 0));

		addOnMoveListener(this);
		
		/* 
		 * Images loading
		 */
		ResourceManager resMgr = ResourceManager.getInstance();
		
		Image defaultImage1 = new Image(resMgr.getResourceAsStream("/images/ghost"+name+"1.png"));
		Image defaultImage2 = new Image(resMgr.getResourceAsStream("/images/ghost"+name+"2.png"));
		defaultImages = new Image[] {defaultImage1, defaultImage2, defaultImage1, defaultImage2};
		
		Image hollow1 = new Image(resMgr.getResourceAsStream("/images/ghosthollow2.png"));
		Image hollow2 = new Image(resMgr.getResourceAsStream("/images/ghosthollow3.png"));
		Image hollow3 = new Image(ResourceManager.getInstance().getResourceAsStream("/images/ghosthollow1.png"));
		hollowImages = new Image[] {hollow2, hollow1, hollow2, hollow1};
		flashHollowImages = new Image[] {hollow3, hollow1, hollow3, hollow1};
		
		images = defaultImages;
		
		/*
		 * Initial status
		 */
		frightened = false;
		initialLocationX = x;
		initialLocationY = y;
		initialDirection = new Direction(1, 0);

		/*
		 * ghost image
		 */
		ImageView ghostNode = new ImageView(defaultImage1);
		ghostNode.xProperty().bind(getXProperty().add(-13));
		ghostNode.yProperty().bind(getYProperty().add(-13));
		ghostNode.imageProperty().bind(imageBinding);
		ghostNode.setCache(true);

		getChildren().add(ghostNode);
	}
	
	public void setOnTurnListener(OnTurnListener listener) {
		this.turnListener = listener;
	}
	
	
	public boolean isFrightened() {
		return this.frightened;
	}

	public void resetPosition()  {
		setX(initialLocationX);
		setY(initialLocationY);
	}
	
	/*
	 *  reset the status of a ghost and place it into the cage */
	public void resetStatus() {
		changeToNormal();
		stayInCageMovesLimit = STAY_IN_CAGE_DEFAULT_MOVES;
		
		resetPosition();
		
		setDirection(new Direction(initialDirection.getDx(), initialDirection.getDy()));

		frightened = false;

		currentImage.set(0);

		images = defaultImages;

		getTimeline().setRate(1.0);

		setVisible(true);
		start();
		
	}

	public void changeToFrightened() {
		frightened = true;

		// switch the animation images
		images = hollowImages;

		// make it move slower
		getTimeline().stop();
		getTimeline().setRate(0.35);
		getTimeline().play();
	}
	
	public void changeToNormal() {
		frightened = false;
		images = defaultImages;
		
		getTimeline().stop();
		getTimeline().setRate(1.0);
		getTimeline().play();
	}
	
	public void changeToFlashingFrightened() {
		images = flashHollowImages;
	}


	private List<Direction> getAvailableDirections() {
		List<Direction> dirs = new ArrayList<>(4);
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x*x != y*y) { // Non si può andare in diagonale e la direzione (0, 0) non è permessa
					int nextX = getX() + x * SharedMazeData.GRID_GAP;
					int nextY = getY() + y * SharedMazeData.GRID_GAP;
					int dataInNextPosition = SharedMazeData.getDataForPosition(nextX, nextY);
					if (dataInNextPosition != SharedMazeData.BLOCK) {
						/* Quando il prossimo punto nella mappa non è un blocco si
						 * può sempre andare in quella direzione a meno che non sia un
						 * blocco CAGE_LIMIT: in quel caso si può andare in quella direzione
						 * solo se si sta andando verso l'alto: x == 0, y == -1 e se stayInCageMovesLimit
						 * è arrivato a 0, questo vuol dire che il fantasmino può uscire dalla gabbia
						 */
						if (dataInNextPosition == SharedMazeData.CAGE_BOUNDARY_LIMIT) {
							if (x == 0 && y == -1 && stayInCageMovesLimit <= 0) {
								dirs.add(new Direction(x, y));
							}
						} else
							dirs.add(new Direction(x, y));
					}
				}
			}
		}
		return dirs;
	}

	private void move() {
		if (stayInCageMovesLimit > 0)
			stayInCageMovesLimit--;
		
		if (SharedMazeData.getDataForPosition(getX(), getY()) != SharedMazeData.INVALID_POINT_IN_MAZE) {
			List<Direction> availableDirs = getAvailableDirections();
			
			
			int hasCurrentDirection = 0;
			if (availableDirs.contains(getDirection()))
				hasCurrentDirection = 1;
			/* Se esclusa la direzione corrente non rimane solo quella opposta,
			allora valuta la possibilità di girare */
			if (availableDirs.size() - hasCurrentDirection > 1) {
				availableDirs.remove(getDirection().getInverse());
				if (turnListener != null)
					turnListener.onTurn(availableDirs);
			}
		}
		
		setX(getX() + (int)getDirection().getDx() * MOVE_SPEED);
		setY(getY() + (int)getDirection().getDy() * MOVE_SPEED);
		
	}
	

	public void hide() {
		setVisible(false);
		getTimeline().stop();
	}

	
	public void onMove() {
		move();
		if ( currentImage.get() < (ANIMATION_STEP - 1) ) {
			currentImage.set(currentImage.get() + 1);
		}
		else {
			currentImage.set(0);
		}
	}
}
