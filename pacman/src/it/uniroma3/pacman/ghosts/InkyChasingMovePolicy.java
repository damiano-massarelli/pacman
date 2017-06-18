package it.uniroma3.pacman.ghosts;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.pacman.PacMan;
import javafx.geometry.Point2D;
import static it.uniroma3.pacman.ghosts.GhostConsts.CHASE_MOVES_LIMIT;

public class InkyChasingMovePolicy extends AbstractMovePolicy {
	
	private Ghost blinky;
	private PacMan pacman;
	

	public InkyChasingMovePolicy(Ghost blinky, PacMan pacman, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.blinky = blinky;
		this.pacman = pacman;
	}


	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		int pacmanFacingX = pacman.getX() + pacman.getDirection().getDeltaX() * 2;
		int pacmanFacingY = pacman.getY() + pacman.getDirection().getDeltaY() * 2;
		
		int deltaX = (pacmanFacingX - blinky.getX()) * 2;
		int deltaY = (pacmanFacingY - blinky.getY()) * 2;
		
		Point2D targetPos = new Point2D(blinky.getX() + deltaX, blinky.getY() + deltaY);
		
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getPosition(), targetPos);
		
		return Collections.min(availableDirections, comparatore);
	}

}
