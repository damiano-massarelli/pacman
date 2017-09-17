package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.PolicyConsts.SCATTER_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class ScatteringMovePolicy implements MovePolicy {
	
	private Point2D scatterTarget;
	
	private int moves;
	private int movesLimit;
	private MovePolicy nextPolicy;

	public ScatteringMovePolicy( Point2D scatterTarget) {
		this.moves = 0;
		this.movesLimit = SCATTER_MOVES_LIMIT;
		this.scatterTarget = scatterTarget;
	}
	
	@Override
	public void setNextPolicy(MovePolicy next) {
		this.nextPolicy = next;
	}
	
	@Override
	public boolean hasNext() {
		this.moves++;
		return this.moves > this.movesLimit;
	}

	@Override
	public MovePolicy next() {
		if (this.moves > this.movesLimit) {
			this.moves = 0;
			return nextPolicy;
		}
		else 
			return this;
	}

	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		Point2D ghostPosition = ghost.getSprite().getPosition(); 
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghostPosition, scatterTarget);
		return Collections.min(availableDirections, comparatore);
	}
	
	@Override
	public int getRemainingMovesToNextPolicy() {
		return this.movesLimit - this.moves;
	}

}
