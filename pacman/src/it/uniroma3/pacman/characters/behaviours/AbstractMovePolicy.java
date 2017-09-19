package it.uniroma3.pacman.characters.behaviours;

import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public abstract class AbstractMovePolicy implements MovePolicy {
	
	private int moves;
	private int movesLimit;
	private MovePolicy nextPolicy;
	
	public AbstractMovePolicy(int movesLimit) {
		this.movesLimit = movesLimit;
		this.moves = 0;
	}

	@Override
	public abstract Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections);

	@Override
	public MovePolicy next() {
		if (getRemainingMovesToNextPolicy() == 0) {
			this.moves = 0;
			return nextPolicy;
		}
		return this;
	}
	
	@Override
	public boolean hasNext() {
		this.moves++;
		return getRemainingMovesToNextPolicy() == 0;
	}

	@Override
	public void setNextPolicy(MovePolicy next) {
		this.nextPolicy = next;
		
	}
	
	public int getRemainingMovesToNextPolicy() {
		return this.movesLimit - this.moves;
	}
	
}