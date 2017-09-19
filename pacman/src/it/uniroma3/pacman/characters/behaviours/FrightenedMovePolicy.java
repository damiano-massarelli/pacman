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

	public FrightenedMovePolicy() {
		this.moves = 0;
		this.movesLimit = FRIGHTENED_MOVES_LIMIT;
	}



	@Override
	public void setNextPolicy(MovePolicy next) {
		this.nextPolicy = next;		
	}



	@Override
	public boolean hasNext() {
		this.moves++;
		return getRemainingMovesToNextPolicy() == 0;
	}

	@Override
	public MovePolicy next() {
		if (getRemainingMovesToNextPolicy() == 0) {
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
