package it.uniroma3.pacman.characters;

import it.uniroma3.pacman.collision.CollidableModelEntity;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.characters.PacManView;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.pacman.staticObjects.Teleport;
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
			newDirection = Direction.LEFT;
		else if (keyboardBuffer == KeyCode.RIGHT)
			newDirection = Direction.RIGHT;
		else if (keyboardBuffer == KeyCode.UP)
			newDirection = Direction.UP;
		else if (keyboardBuffer == KeyCode.DOWN)
			newDirection = Direction.DOWN;
		
		if (newDirection != null)
			pacmanView.attemptToSetDirection(newDirection);

	}


	public void setKeyboardBuffer(KeyCode code) {
		keyboardBuffer = code;
	}
	
	public void reset() {
		this.getView().resetStatus();
		this.keyboardBuffer = null;
	}
	
	@Override
	public PacManView getView() {
		return pacmanView;
	}

	@Override
	public void onMove() {
		handleKeyboardInput();
	}

	@Override
	public void accept(CollisionHandler visitor, CollidableModelEntity other) {
		other.collidedWith(this, visitor);
		
	}

	@Override
	public void collidedWith(PacMan pacMan, CollisionHandler visitor) {
		// PacMan can't collide with itself
	}

	@Override
	public void collidedWith(Ghost ghost, CollisionHandler visitor) {
		visitor.handle(this, ghost);
	}

	@Override
	public void collidedWith(Dot dot, CollisionHandler visitor) {
		visitor.handle(this, dot);
		
	}

	@Override
	public void collidedWith(MagicDot magicDot, CollisionHandler visitor) {
		visitor.handle(this, magicDot);
		
	}

	@Override
	public void collidedWith(Teleport teleport, CollisionHandler visitor) {
		visitor.handle(this, teleport);
		
	}

	
	
}
