package it.uniroma3.pacman.ghosts;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.pacman.PacMan;
import static it.uniroma3.pacman.ghosts.GhostConsts.CHASE_MOVES_LIMIT;

public class ClydeChasingMovePolicy extends AbstractMovePolicy {
	
	private int SOGLIA_COMPORTAMENTO_BLINKY = 8 * SharedMazeData.GRID_GAP;
	
	private PacMan pacman;
	

	public ClydeChasingMovePolicy(PacMan pacman, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacman = pacman;
	}

	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		Direction direzioneScelta = null;
		
		double distance = ghost.getPosition().distance(pacman.getPosition());
		
		if (distance > SOGLIA_COMPORTAMENTO_BLINKY) {
			ComparatoreDirezione comparatoreDirezione = new ComparatoreDirezione(ghost.getPosition(), pacman.getPosition());
			direzioneScelta = Collections.min(availableDirections, comparatoreDirezione);
		}
		else {
			Collections.shuffle(availableDirections);
			direzioneScelta = availableDirections.get(0);
		}
		return direzioneScelta;
	}

}
