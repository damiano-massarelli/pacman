package it.uniroma3.pacman.characters.behaviours;

import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class AbstractMovePolicyTestImpl extends AbstractMovePolicy {

	public AbstractMovePolicyTestImpl(int movesLimit) {
		super(movesLimit);
	}

	@Override
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections) {
		return null;
	}

}
