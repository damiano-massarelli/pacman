package it.uniroma3.pacman.ghosts;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.pacman.PacMan;
import javafx.geometry.Point2D;

import static it.uniroma3.pacman.ghosts.GhostConsts.CHASE_MOVES_LIMIT;

public class PinkyChasingMovePolicy extends AbstractMovePolicy {

	private PacMan pacman;
	
	public PinkyChasingMovePolicy(PacMan pacman, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacman = pacman;
	}

	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		Point2D targetPos = new Point2D(pacman.getX() + pacman.getDirection().getDeltaX() * 4, 
				pacman.getY() + pacman.getDirection().getDeltaY() * 4);
		
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getPosition(), targetPos);
		return Collections.min(availableDirections, comparatore);
	}

}
