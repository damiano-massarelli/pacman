package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.SCATTER_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class ScatteringMovePolicy extends AbstractMovePolicy {
	
	private Point2D scatterTarget;
	

	public ScatteringMovePolicy(MovePolicy nextPolicy, Point2D scatterTarget) {
		super(nextPolicy, SCATTER_MOVES_LIMIT);
		this.scatterTarget = scatterTarget;
	}



	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getSprite().getPosition(), scatterTarget);
		return Collections.min(availableDirections, comparatore);
	}

}
