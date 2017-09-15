package it.uniroma3.pacman.graphics.characters;

import java.util.ArrayList;
import java.util.List;
import it.uniroma3.resources.ResourceManager;
import it.uniroma3.pacman.maze.BlockType;
import it.uniroma3.pacman.maze.MazeBlockMatrix;
import it.uniroma3.pacman.maze.MazeConstants;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.AnimatedSprite;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * Ghost.fx created on 2009-1-28, 14:26:09 <br>
 * Ghost.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class GhostSprite extends AnimatedSprite implements OnMoveListener{
	
	private static final int STAY_IN_CAGE_DEFAULT_MOVES = 120;

	// the images of animation
	private final Image[] defaultImages;
	
	private final Image[] hollowImages;
	
	private final Image[] flashHollowImages;

	// initial direction and position of a ghost, used in status reset
	private Point2D initialPosition;
	private Direction initialDirection;


	// the flag is set if a ghost becomes hollow
	
	private int stayInCageMovesLimit;
	
	private Direction direction;
	
	private OnTurnListener turnListener;
		
	private MazeBlockMatrix blockMatrix;

	// the GUI of a ghost
	public GhostSprite(String name, int x, int y, MazeBlockMatrix blockMatrix) {
		super(null, x, y);
		setCollisionRadius(10);
		addOnMoveListener(this);
		
		this.blockMatrix = blockMatrix;
		
		/* 
		 * Image loading
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
		
		setImages(defaultImages);
		
		direction = Direction.RIGHT;
		
		/*
		 * Initial status
		 */
		initialDirection = direction;
		initialPosition = new Point2D(x, y);

	}
	
	public void setOnTurnListener(OnTurnListener listener) {
		this.turnListener = listener;
	}
	
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/*
	 *  reset the status of a ghost and place it into the cage */
	public void resetStatus() {
		changeToNormal();
		stayInCageMovesLimit = STAY_IN_CAGE_DEFAULT_MOVES;
		
		setPosition(initialPosition);
		
		setDirection(initialDirection);

		setImages(defaultImages);

		getTimeline().setRate(1.0);

		setVisible(true);
		start();
		
	}

	public void changeToFrightened() {
		// switch the animation images
		setImages(hollowImages);

		// make it move slower
		getTimeline().stop();
		getTimeline().setRate(0.35);
		getTimeline().play();
	}
	
	
	public void changeToNormal() {
		setImages(defaultImages);
		
		getTimeline().stop();
		getTimeline().setRate(1.0);
		getTimeline().play();
	}
	

	public void changeToFlashingFrightened() {
		setImages(flashHollowImages);
	}


	private List<Direction> getAvailableDirections() {
		List<Direction> dirs = new ArrayList<>(4);
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (x*x != y*y) { // Non si può andare in diagonale e la direzione (0, 0) non è permessa
					Point2D nextPosition = getPosition().add(MazeConstants.GRID_GAP * x, MazeConstants.GRID_GAP * y);
					BlockType dataInNextPosition = blockMatrix.getBlockTypeAtPosition(nextPosition);
					if (dataInNextPosition != BlockType.BLOCK) {
						/* Quando il prossimo punto nella mappa non è un blocco si
						 * può sempre andare in quella direzione a meno che non sia un
						 * blocco CAGE_LIMIT: in quel caso si può andare in quella direzione
						 * solo se si sta andando verso l'alto: x == 0, y == -1 e se stayInCageMovesLimit
						 * è arrivato a 0, questo vuol dire che il fantasmino può uscire dalla gabbia
						 */
						if (dataInNextPosition == BlockType.CAGE_BOUNDARY) {
							if (x == 0 && y == -1 && stayInCageMovesLimit <= 0) {
								dirs.add(Direction.fromXY(x, y));
							}
						} else
							dirs.add(Direction.fromXY(x, y));
					}
				}
			}
		}
		return dirs;
	}

	@Override
	public void onMove() {
		if (stayInCageMovesLimit > 0)
			stayInCageMovesLimit--;
		
		if (blockMatrix.getBlockTypeAtPosition(getPosition()) != BlockType.IVALID) {
			List<Direction> availableDirs = getAvailableDirections();
			
			
			int hasCurrentDirection = 0;
			if (availableDirs.contains(getDirection()))
				hasCurrentDirection = 1;
			/* Se esclusa la direzione corrente non rimane solo quella opposta,
			allora valuta la possibilità di girare */
			if (availableDirs.size() - hasCurrentDirection > 1) {
				System.out.println(getDirection());
				availableDirs.remove(getDirection().getInverse());
				if (turnListener != null)
					turnListener.onTurn(availableDirs);
			}
		}
		
		setX(getX() + (int)getDirection().getDirX() * MOVE_SPEED);
		setY(getY() + (int)getDirection().getDirY() * MOVE_SPEED);
		
	}
	
	
}
