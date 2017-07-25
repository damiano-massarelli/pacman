package it.uniroma3.pacman.characters;

import it.uniroma3.pacman.game.PacmanGame;
import it.uniroma3.pacman.graphics.DyingPacManView;
import it.uniroma3.pacman.graphics.PacManView;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.MovingObject;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * PacMan.fx created on 2009-1-1, 11:50:58 <br>
 * PacMan.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class PacMan implements OnMoveListener {
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
	private int keyboardBuffer;

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
		
		keyboardBuffer = -1;
		
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

		System.out.println(":)))");
		
		if (keyboardBuffer < 0) {
			return;
		}
		Direction newDirection = null;
		if (keyboardBuffer == MovingObject.MOVE_LEFT)
			newDirection = Direction.WEST;
		else if (keyboardBuffer == MovingObject.MOVE_RIGHT)
			newDirection = Direction.EST;
		else if (keyboardBuffer == MovingObject.MOVE_UP)
			newDirection = Direction.NORTH;
		else if (keyboardBuffer == MovingObject.MOVE_DOWN)
			newDirection = Direction.SOUTH;
		
		if (pacmanView.attemptToSetDirection(newDirection) == true);
			//imageDirection.set(keyboardBuffer); // TODO: da cambiare se cambia handleKeyboardInput

	}


	public void setKeyboardBuffer(int k) {
		keyboardBuffer = k;
	}
	
	
	public PacManView getPacmanView() {
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
