package it.uniroma3.pacman.ghosts;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.PacManView;
import it.uniroma3.pacman.movingObjects.Direction;

import static it.uniroma3.pacman.ghosts.GhostConsts.CHASE_MOVES_LIMIT;

public class BlinkyChasingMovePolicy extends AbstractMovePolicy {
	
	private PacManView pacManView;
	
	public BlinkyChasingMovePolicy(PacManView pacManView, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacManView = pacManView;
	}


	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getGhostView().getPosition(), pacManView.getPosition());
		return Collections.min(availableDirections, comparatore); // c'e' sempre almeno una direzione
	}


}
