package it.uniroma3.pacman.characters.behaviours;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.Sprite;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.maze.MazeConstants;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;
import static it.uniroma3.pacman.characters.behaviours.PolicyConsts.CHASE_MOVES_LIMIT;

public class ChasingMovePolicy implements MovePolicy {
	
	private int SOGLIA_COMPORTAMENTO_BLINKY = 8 * MazeConstants.GRID_GAP;
	
	// Usato per il comportamento di inky
	private Sprite blinkySprite;

	private int moves;
	private int movesLimit;
	private MovePolicy nextPolicy;
	
	private PacManSprite pacManSprite;

	public ChasingMovePolicy(PacManSprite pacManSprite, MovePolicy nextPolicy, Sprite blinkySprite) {
		this.nextPolicy = nextPolicy;
		this.movesLimit = CHASE_MOVES_LIMIT;
		this.moves = 0;
		
		this.pacManSprite = pacManSprite;
		this.blinkySprite = blinkySprite;
	}

	@Override
	public boolean hasNext() {
		this.moves++;
		return this.moves > this.movesLimit;
	}

	@Override
	public MovePolicy next() {
		if (this.moves > this.movesLimit) {
			this.moves = 0;
			return nextPolicy;
		}
		else 
			return this;
	}

	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		Point2D ghostPosition = ghost.getSprite().getPosition();
		
		Direction choosenDirection = null;
		
		/* --- BLINKY --- */
		if (ghost.getName().equals("blinky")) {
			ComparatoreDirezione comparatore = new ComparatoreDirezione(ghostPosition, pacManSprite.getPosition());
			choosenDirection = Collections.min(availableDirections, comparatore); // c'e' sempre almeno una direzione
		}
		
		/* --- CLYDE --- */
		else if (ghost.getName().equals("clyde")) {
			double distance = ghostPosition.distance(pacManSprite.getPosition());
			
			if (distance > SOGLIA_COMPORTAMENTO_BLINKY) {
				ComparatoreDirezione comparatoreDirezione = new ComparatoreDirezione(ghostPosition, pacManSprite.getPosition());
				choosenDirection = Collections.min(availableDirections, comparatoreDirezione);
			}
			else {
				Collections.shuffle(availableDirections);
				choosenDirection = availableDirections.get(0);
			}
		}
		
		else if (ghost.getName().equals("pinky")) {
			Point2D targetPos = new Point2D(pacManSprite.getX() + pacManSprite.getDirection().getDeltaX() * 4, 
					pacManSprite.getY() + pacManSprite.getDirection().getDeltaY() * 4);
			
			ComparatoreDirezione comparatore = new ComparatoreDirezione(ghostPosition, targetPos);
			choosenDirection = Collections.min(availableDirections, comparatore);
		}
		
		else if (ghost.getName().equals("inky")) {
			int pacmanFacingX = pacManSprite.getX() + pacManSprite.getDirection().getDeltaX() * 2;
			int pacmanFacingY = pacManSprite.getY() + pacManSprite.getDirection().getDeltaY() * 2;
			
			int deltaX = (pacmanFacingX - blinkySprite.getX()) * 2;
			int deltaY = (pacmanFacingY - blinkySprite.getY()) * 2;
			
			Point2D targetPos = new Point2D(blinkySprite.getX() + deltaX, blinkySprite.getY() + deltaY);
			
			ComparatoreDirezione comparatore = new ComparatoreDirezione(ghostPosition, targetPos);
			
			choosenDirection = Collections.min(availableDirections, comparatore);
		}
		
		return choosenDirection;
	}

	@Override
	public int getRemainingMovesToNextPolicy() {
		return this.movesLimit - this.moves;
	}
}
