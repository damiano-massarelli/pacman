package it.uniroma3.pacman.ghosts;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.PacManView;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;
import static it.uniroma3.pacman.ghosts.GhostConsts.CHASE_MOVES_LIMIT;

public class InkyChasingMovePolicy extends AbstractMovePolicy {
	
	private Ghost blinky;
	private PacManView pacManView;
	

	public InkyChasingMovePolicy(Ghost blinky, PacManView pacManView, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.blinky = blinky;
		this.pacManView = pacManView;
	}


	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		int pacmanFacingX = pacManView.getX() + pacManView.getDirection().getDeltaX() * 2;
		int pacmanFacingY = pacManView.getY() + pacManView.getDirection().getDeltaY() * 2;
		
		int deltaX = (pacmanFacingX - blinky.getX()) * 2;
		int deltaY = (pacmanFacingY - blinky.getY()) * 2;
		
		Point2D targetPos = new Point2D(blinky.getX() + deltaX, blinky.getY() + deltaY);
		
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getPosition(), targetPos);
		
		return Collections.min(availableDirections, comparatore);
	}

}
