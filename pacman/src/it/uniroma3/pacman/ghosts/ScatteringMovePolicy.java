package it.uniroma3.pacman.ghosts;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;
import static it.uniroma3.pacman.ghosts.GhostConsts.SCATTER_MOVES_LIMIT;

public class ScatteringMovePolicy extends AbstractMovePolicy {
	
	private Point2D scatterTarget;
	

	public ScatteringMovePolicy(MovePolicy nextPolicy, Point2D scatterTarget) {
		super(nextPolicy, SCATTER_MOVES_LIMIT);
		this.scatterTarget = scatterTarget;
	}



	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getView().getPosition(), scatterTarget);
		return Collections.min(availableDirections, comparatore);
	}

}
