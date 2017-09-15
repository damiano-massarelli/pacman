package it.uniroma3.pacman.characters.behaviours;
import static it.uniroma3.pacman.characters.behaviours.PolicyConsts.FRIGHTENED_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.movingObjects.Direction;

public class FrightenedMovePolicy implements MovePolicy {
	
	private int moves;
	private int movesLimit;
	private MovePolicy nextPolicy;

	public FrightenedMovePolicy(MovePolicy nextPolicy) {
		this.nextPolicy = nextPolicy;
		this.moves = 0;
		this.movesLimit = FRIGHTENED_MOVES_LIMIT;
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
		Collections.shuffle(availableDirections);
		return availableDirections.get(0);
	}
	
	@Override
	public int getRemainingMovesToNextPolicy() {
		return this.movesLimit - this.moves;
	}
}
