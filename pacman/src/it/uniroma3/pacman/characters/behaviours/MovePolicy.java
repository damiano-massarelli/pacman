package it.uniroma3.pacman.characters.behaviours;

import java.util.Iterator;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.movingObjects.Direction;

public interface MovePolicy extends Iterator<MovePolicy> {
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections);
	
	public int getRemainingMovesToNextPolicy();
	
	public void setNextPolicy(MovePolicy next);
}
