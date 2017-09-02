package it.uniroma3.pacman.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.collision.CollisionDetector;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.characters.OnAnimationEndListener;
import it.uniroma3.pacman.maze.MazeAssets;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.Teleport;
import javafx.beans.property.SimpleIntegerProperty;



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
	
	private MazeAssets mazeAssets;

	public PacmanGame(MazeAssets mazeAssets) throws IOException {
		this.mazeAssets = mazeAssets;
		
		this.level = new SimpleIntegerProperty(1);
		ghosts = new ArrayList<Ghost>();
		
		
		new CharactersSetup().setup(this);
		
		
		gameView = new PacmanGameView(mazeAssets, level, pacMan.getScoreProperty(), pacMan.getLivesProperty());
		this.gameView.setPacManSprite(pacMan.getSprite());
		for (Ghost g : ghosts)
			this.gameView.addGhostSprite(g.getSprite());
		
		waitingForStart = true;

		collisionDetector = new CollisionDetector(new CollisionHandler(this));
		collisionDetector.addCollidable(pacMan);
		for (Ghost g : ghosts)
			collisionDetector.addCollidable(g);
		for (Dot d : mazeAssets.getDots())
			collisionDetector.addCollidable(d);
		for (Teleport t : mazeAssets.getTeleports())
			collisionDetector.addCollidable(t);
		
		this.gameView.setOnKeyPressed(new KeyboardEventHandler(this));
	}
	
	public PacmanGameView getView() {
		return this.gameView;
	}
	
	public MazeAssets getMazeAssets() {
		return this.mazeAssets;
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
		for (Dot d : mazeAssets.getDots())
			d.setEaten(false);
		
		this.collisionDetector.startDetecting();
		level.set(1);
		pacMan.setScore(0);
		pacMan.setDotEatenCount(0);

		pacMan.getLivesProperty().set(3);
		this.gameView.startNewGame();
		pacMan.reset();
		for (Ghost g : ghosts)
			g.reset();
	}

	// reset status and start a new level
	public void startNewLevel() {
		for (Dot d : mazeAssets.getDots())
			d.setEaten(false);
		pacMan.setDotEatenCount(0);
		level.set(level.get() + 1);
		this.gameView.startNewLevel();
		pacMan.reset();
		for (Ghost g : ghosts)
			g.reset();
	}

	// reset status and start a new life
	public void startNewLife() {
		this.collisionDetector.startDetecting();
		this.pacMan.reset();
		for (Ghost g : this.ghosts)
			g.reset();
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
