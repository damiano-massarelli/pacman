package it.uniroma3.pacman.ghosts;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.pacman.PacMan;
import static it.uniroma3.pacman.ghosts.GhostConsts.CHASE_MOVES_LIMIT;

public class BlinkyChasingMovePolicy extends AbstractMovePolicy {
	
	private PacMan pacman;
	
	public BlinkyChasingMovePolicy(PacMan pacman, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacman = pacman;
	}


	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getPosition(), pacman.getPosition());
		return Collections.min(availableDirections, comparatore); // c'e' sempre almeno una direzione
	}


}
