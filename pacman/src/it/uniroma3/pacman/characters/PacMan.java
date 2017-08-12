package it.uniroma3.pacman.characters;

import it.uniroma3.pacman.characterGraphics.DyingPacManView;
import it.uniroma3.pacman.characterGraphics.PacManView;
import it.uniroma3.pacman.game.PacmanGame;
import it.uniroma3.pacman.graphics.CollidableModelEntity;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.AnimatedView;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;

/**
 * PacMan.fx created on 2009-1-1, 11:50:58 <br>
 * PacMan.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class PacMan implements OnMoveListener, CollidableModelEntity {
	/**
	 * if score/10'000 is different from lastScoreInTenThausands
	 * add a life to pacman
	 */
	private int lastScoreInTenThousands = 0;
	
	private int dotEatenCount;
	
	private SimpleIntegerProperty lives;

	private SimpleIntegerProperty score;

	/**
	 * Buffer to keep the keyboard input.
	 */
	private KeyCode keyboardBuffer;

	/**
	 * The pacman graphics
	 */
	private PacManView pacmanView;

	/**
	 * Constructor.
	 *
	 * @param x
	 * @param y
	 */
	public PacMan() {
		
		dotEatenCount = 0;
		score = new SimpleIntegerProperty(0);   
		lives = new SimpleIntegerProperty(3);
		
		keyboardBuffer = null;
		
		this.pacmanView = new PacManView();
		pacmanView.addOnMoveListener(this);
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

	/**
	 * Handle keyboard input.
	 */
	private void handleKeyboardInput() {		
		if (keyboardBuffer == null) {
			return;
		}
		Direction newDirection = null;
		if (keyboardBuffer == KeyCode.LEFT)
			newDirection = Direction.WEST;
		else if (keyboardBuffer == KeyCode.RIGHT)
			newDirection = Direction.EST;
		else if (keyboardBuffer == KeyCode.UP)
			newDirection = Direction.NORTH;
		else if (keyboardBuffer == KeyCode.DOWN)
			newDirection = Direction.SOUTH;
		
		pacmanView.attemptToSetDirection(newDirection);

	}


	public void setKeyboardBuffer(KeyCode code) {
		keyboardBuffer = code;
	}
	
	@Override
	public PacManView getView() {
		return pacmanView;
	}

	
	// TODO: not here, DyingPacmanView should be created within a collision handler
	public void die(PacmanGame game) {
		pacmanView.hide();
		DyingPacManView dyingPacMan = new DyingPacManView(game);
		dyingPacMan.startAnimation(pacmanView.getX(), pacmanView.getY());
	}

	@Override
	public void onMove() {
		handleKeyboardInput();
	}

}
