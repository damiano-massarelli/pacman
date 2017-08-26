package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;

public class BlinkyChasingMovePolicy extends AbstractMovePolicy {
	
	private PacManSprite pacManView;
	
	public BlinkyChasingMovePolicy(PacManSprite pacManView, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacManView = pacManView;
	}


	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getSprite().getPosition(), pacManView.getPosition());
		return Collections.min(availableDirections, comparatore); // c'e' sempre almeno una direzione
	}


}
