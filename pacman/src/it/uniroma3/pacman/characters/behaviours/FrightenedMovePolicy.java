package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.FRIGHTENED_MOVES_LIMIT;
import java.util.List;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;
public class FrightenedMovePolicy extends AbstractMovePolicy {
	

	public FrightenedMovePolicy(MovePolicy nextPolicy) {
		super(nextPolicy, FRIGHTENED_MOVES_LIMIT);
	}


	@Override
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections) {
		return availableDirections.get((int) (Math.random() * availableDirections.size()));
	}
}
