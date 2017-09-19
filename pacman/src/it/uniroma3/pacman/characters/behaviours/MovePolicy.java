package it.uniroma3.pacman.characters.behaviours;

import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.movingObjects.Direction;

public interface MovePolicy {
	public boolean hasNext();
	
	public MovePolicy next();
	
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections);
	
	public int getRemainingMovesToNextPolicy();
	
	public void setNextPolicy(MovePolicy next);
}
