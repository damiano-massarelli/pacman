package it.uniroma3.pacman.collision;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.game.PacmanGame;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.pacman.staticObjects.Teleport;
import it.uniroma3.pacman.ui.ScoreText;
import javafx.scene.layout.Pane;

/**
 * Un oggetto di una classe che implementa questa interfaccia può essere utilizzato
 * come Gestore delle collisioni per un {@link CollisionDetector}
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
	/**
	 * handle viene chiamato dal {@link CollisionDetector} a cui questo handler è
	 * stato aggiunto quando si verifica una collisione tra due oggetti gestiti 
	 * dal {@link CollisionDetector}
	 * @param o1 il primo oggetto
	 * @param o2 il secondo oggetto
	 */
	public void handle(PacMan pacMan, Ghost ghost) {
		if (ghost.isFrightened()) {
			ghost.getView().resetStatus();
			int score = GHOST_EATEN_SCORE * ghostsEatenScoreMultiplier;
			pacMan.setScore(pacMan.getScore() + score);
			ghostsEatenScoreMultiplier *= 2;
			
			Pane gameField = pacmanGame.getView().getGameField();
			gameField.getChildren().add(new ScoreText(""+score, gameField, pacMan.getView().getX(), pacMan.getView().getY()));
		} else {
			pacmanGame.startNewLife();
		}
	}
	
	public void handle(PacMan pacMan, Dot dot) {
		if (!dot.isEaten()) {
			dot.setEaten(true);
			pacMan.setDotEatenCount(pacMan.getDotEatenCount() + 1);
			pacMan.setScore(pacMan.getScore() + DOT_EATEN_SCORE);
		}
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
	}
	
	public void handle(PacMan pacMan, Teleport teleport) {
		
	}
	
	public void handle(Ghost ghost, Teleport teleport) {
		
	}

}
