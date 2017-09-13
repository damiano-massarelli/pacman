package it.uniroma3.pacman.collision;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.game.PacmanGame;
import it.uniroma3.pacman.graphics.characters.DyingPacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.pacman.staticObjects.Teleport;
import it.uniroma3.pacman.ui.ScoreText;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

/**
 * A CollisionHandler is responsible for handling collisions among objects which are detected
 * by a specific {@link CollisionDetector}.
 * @author damiano
 *
 */
public class CollisionHandler {
	
	private static final int GHOST_EATEN_SCORE = 200;
	private static final int DOT_EATEN_SCORE = 10;
	private static final int MAGIC_DOT_EATEN_SCORE = 50;
	
	private PacmanGame pacmanGame;
	
	private int ghostsEatenScoreMultiplier;
	
	public CollisionHandler(PacmanGame pacmanGame) {
		this.pacmanGame = pacmanGame;
		ghostsEatenScoreMultiplier = 2;
	}
	
	public void handle(PacMan pacMan, Ghost ghost) {
		Pane gameField = pacmanGame.getView().getGameField();
		if (ghost.isFrightened()) {
			ghost.reset();
			int score = GHOST_EATEN_SCORE * ghostsEatenScoreMultiplier;
			pacMan.setScore(pacMan.getScore() + score);
			ghostsEatenScoreMultiplier *= 2;
			
			gameField.getChildren().add(new ScoreText(String.valueOf(score), gameField, pacMan.getSprite().getX(), pacMan.getSprite().getY()));
		} else {
			pacMan.setLives(pacMan.getLives() - 1);
			
			pacmanGame.getCollisionDetector().stopDetecting();
			pacmanGame.getView().stopAndHideCharacters();
			
			DyingPacManSprite dyingPacManSprite = new DyingPacManSprite(pacMan.getSprite().getPosition(), gameField);
			dyingPacManSprite.setOnAnimationEndListener(pacmanGame);
			gameField.getChildren().add(dyingPacManSprite);
		}
	}
	
	public void handle(PacMan pacMan, Dot dot) {
		if (!dot.isEaten()) {
			dot.setEaten(true);
			pacMan.setDotEatenCount(pacMan.getDotEatenCount() + 1);
			pacMan.setScore(pacMan.getScore() + DOT_EATEN_SCORE);
		}
		if (pacMan.getDotEatenCount() >= pacmanGame.getMazeAssets().getDots().size())
			pacmanGame.levelCompleted();
	}
	
	public void handle(PacMan pacMan, MagicDot magicDot) {
		if (!magicDot.isEaten()) {
			magicDot.setEaten(true);
			pacMan.setDotEatenCount(pacMan.getDotEatenCount() + 1);
			pacMan.setScore(pacMan.getScore() + MAGIC_DOT_EATEN_SCORE);
			for (Ghost g : this.pacmanGame.getGhosts())
				g.changeToFrightened();
			
			ghostsEatenScoreMultiplier = 1;
		}
		if (pacMan.getDotEatenCount() >= pacmanGame.getMazeAssets().getDots().size())
			pacmanGame.levelCompleted();
	}
	
	public void handle(PacMan pacMan, Teleport teleport) {
		Direction pacManDirection = pacMan.getSprite().getDirection();
		Point2D nextTeleportPosition = teleport.getNextTeleport().getSprite().getPosition();
		Point2D nextPosition = nextTeleportPosition.add(pacManDirection.getDeltaX(), pacManDirection.getDeltaY());
		pacMan.getSprite().setPosition(nextPosition);
	}
	
	public void handle(Ghost ghost, Teleport teleport) {
		Direction ghostDirection = ghost.getSprite().getDirection();
		Point2D nextTeleportPosition = teleport.getNextTeleport().getSprite().getPosition();
		Point2D nextPosition = nextTeleportPosition.add(ghostDirection.getDeltaX(), ghostDirection.getDeltaY());
		ghost.getSprite().setPosition(nextPosition);
	}
}
