package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.FALSING_FRIGHTENED_MOVES_LIMIT;
import static it.uniroma3.pacman.characters.behaviours.GhostConsts.FRIGHTENED_MOVES_LIMIT;

import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.movingObjects.Direction;
public class FrightenedMovePolicy extends AbstractMovePolicy {
	
	private Ghost ghost;
	
	
	public FrightenedMovePolicy(MovePolicy nextPolicy, Ghost ghost) {
		super(nextPolicy, FRIGHTENED_MOVES_LIMIT);
		this.ghost = ghost;
	}


	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		return availableDirections.get((int) (Math.random() * availableDirections.size()));
	}


	@Override
	public MovePolicy nextPolicy() {
		MovePolicy next = super.nextPolicy();
		if (getRemainingMovesToNextPolicy() == FALSING_FRIGHTENED_MOVES_LIMIT && ghost.isFrightened())
			ghost.getSprite().changeToFlashingFrightened();
		else if (getRemainingMovesToNextPolicy() == 0)
			ghost.getSprite().changeToNormal();
		return next;
	}
}
