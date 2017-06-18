package it.uniroma3.pacman.ghosts;

import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;

public interface MovePolicy {
	// meglio posizione invece di ghost
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections);
	
	public MovePolicy nextPolicy();
	
	public void setNextPolicy(MovePolicy next);
}
