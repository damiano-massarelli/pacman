package it.uniroma3.pacman.characters.behaviours;

import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public interface MovePolicy {
	public boolean hasNext();
	
	public MovePolicy next();
	
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections);
	
	public int getRemainingMovesToNextPolicy();
	
	public void setNextPolicy(MovePolicy next);
}
