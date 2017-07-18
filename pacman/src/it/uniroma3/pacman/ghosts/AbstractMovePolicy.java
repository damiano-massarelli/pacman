package it.uniroma3.pacman.ghosts;

import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.movingObjects.Direction;

public abstract class AbstractMovePolicy implements MovePolicy {
	
	private int moves;
	private int movesLimit;
	private MovePolicy nextPolicy;
	
	public AbstractMovePolicy(MovePolicy nextPolicy, int movesLimit) {
		this.nextPolicy = nextPolicy;
		this.movesLimit = movesLimit;
		this.moves = 0;
	}

	@Override
	public abstract Direction makeDecision(Ghost ghost, List<Direction> availableDirections);

	@Override
	public MovePolicy nextPolicy() {
		this.moves++;
		if (this.moves > this.movesLimit) {
			this.moves = 0;
			return nextPolicy;
		}
		return this;
	}

	@Override
	public void setNextPolicy(MovePolicy next) {
		this.nextPolicy = next;
		
	}
	public int getRemainingMovesToNextPolicy() {
		return this.movesLimit - this.moves;
	}
	
}