package it.uniroma3.pacman.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.collision.CollisionDetector;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.characters.OnAnimationEndListener;
import it.uniroma3.pacman.maze.MazeFileLoader;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.staticObjects.Dot;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * Maze.fx created on 2<<008-12-20, 20:22:15 <br>
 * Maze.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */

public class PacmanGame implements OnAnimationEndListener {

	// Pac-Man Character
	private PacMan pacMan;

	private final List<Ghost> ghosts;

	// level of the game
	private SimpleIntegerProperty level;
	
	private boolean lastGameResultIsGameOver = true;

	private boolean waitingForStart;
	
	private CollisionDetector collisionDetector;
	
	private PacmanGameView gameView;

	public PacmanGame() throws IOException {
		MazeFileLoader builder = new MazeFileLoader("/resources/maze.txt");
		builder.readMazeData();
		
		this.level = new SimpleIntegerProperty(1);
		ghosts = new ArrayList<Ghost>();
		
		
		new CharactersSetup().setup(this);
		
		
		gameView = new PacmanGameView(level, pacMan.getScoreProperty(), pacMan.getLivesProperty());
		this.gameView.setPacManSprite(pacMan.getSprite());
		for (Ghost g : ghosts)
			this.gameView.addGhostSprite(g.getSprite());
		
		waitingForStart = true;

		collisionDetector = new CollisionDetector(new CollisionHandler(this));
		collisionDetector.addCollidable(pacMan);
		for (Ghost g : ghosts)
			collisionDetector.addCollidable(g);
		for (Dot d : SharedMazeData.getDots())
			collisionDetector.addCollidable(d);
		// Collision handlers
//		AutomaticCollisionHandler auto = new AutomaticCollisionHandler();
//		auto.addCollisionHandler(new PacManDotCollisionHandler(this), PacManView.class, DotView.class);
//		PacManGhostCollisionHandler pacManGhostCollisionHandler = new PacManGhostCollisionHandler(ghosts, this);
//		auto.addCollisionHandler(pacManGhostCollisionHandler, PacManView.class, Ghost.class);
//		auto.addCollisionHandler(new PacManMagicDotCollisionHandler(ghosts, pacManGhostCollisionHandler), PacManView.class, MagicDotView.class);
//		
//		
//		MovingObjectTeleportCollisionHandler teleportHandler = new MovingObjectTeleportCollisionHandler();
//		auto.addCollisionHandler(teleportHandler, PacManView.class, Teleport.class);
//		auto.addCollisionHandler(teleportHandler, Ghost.class, Teleport.class);
//		
//		collisionDetector = new CollisionDetector();
//		collisionDetector.addCollidable(pacMan.getPacmanView());
//		for (Ghost g : ghosts)
//			collisionDetector.addCollidable(g.getGhostView());
//		collisionDetector.addTrigger(new DotCollisionTrigger(pacMan));
//		collisionDetector.addTrigger(new TeleportCollisionTrigger(pacMan, ghosts));
//		collisionDetector.addCollisionHandler(auto);
		
		this.gameView.setOnKeyPressed(new KeyboardEventHandler(this));
	}
	
	public PacmanGameView getView() {
		return this.gameView;
	}
	
	public CollisionDetector getCollisionDetector() {
		return this.collisionDetector;
	}
	
	public void addGhost(Ghost ghost) {
		ghosts.add(ghost);
	}

	public List<Ghost> getGhosts() {
		return this.ghosts;
	}
	
	public PacMan getPacMan() {
		return pacMan;
	}

	public void setPacMan(PacMan pacMan) {
		this.pacMan = pacMan;
		
	}

	public boolean lastGameResultIsGameOver() {
		return lastGameResultIsGameOver;
	}

	public void setLastGameResultIsGameOver(boolean lastGameResultIsGameOver) {
		this.lastGameResultIsGameOver = lastGameResultIsGameOver;
	}

	public boolean isWaitingForStart() {
		return waitingForStart;
	}

	public void setWaitingForStart(boolean waitingForStart) {
		this.waitingForStart = waitingForStart;
	}


	public void gameOver() {
		lastGameResultIsGameOver = true;
		waitingForStart = true;
		this.gameView.gameOver();
	}
	
	public void levelCompleted() {
		lastGameResultIsGameOver = false;
		waitingForStart = true;
		this.gameView.levelCompleted();
	}

	// reset status and start a new game
	public void startNewGame() {
		this.collisionDetector.startDetecting();
		level.set(1);
		
//		SharedMazeData.resetDots();
		pacMan.setScore(0);
		pacMan.setDotEatenCount(0);

		pacMan.getLivesProperty().set(3);
		this.gameView.startNewGame();
	}

	// reset status and start a new level
	public void startNewLevel() {
//		SharedMazeData.resetDots();
		pacMan.setDotEatenCount(0);
		level.set(level.get() + 1);
		this.gameView.startNewLevel();
	}

	// reset status and start a new life
	public void startNewLife() {
		this.collisionDetector.startDetecting();
		this.pacMan.reset();
		this.gameView.startNewLife();
	}
	
	/** This method is called by DyingPacManSprite when its animation is over */
	@Override
	public void onAnimationEnd() {
		if (pacMan.getLives() == 0)
			this.gameOver();
		else
			this.startNewLife();
		
	}

}
