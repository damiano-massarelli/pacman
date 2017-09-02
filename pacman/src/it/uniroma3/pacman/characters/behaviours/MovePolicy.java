package it.uniroma3.pacman.characters.behaviours;

import java.util.Iterator;
import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public interface MovePolicy extends Iterator<MovePolicy> {
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections);
	
	public int getRemainingMovesToNextPolicy();
}
