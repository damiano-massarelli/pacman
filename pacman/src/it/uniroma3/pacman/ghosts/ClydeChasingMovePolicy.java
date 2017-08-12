package it.uniroma3.pacman.ghosts;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characterGraphics.PacManView;
import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.movingObjects.Direction;

import static it.uniroma3.pacman.ghosts.GhostConsts.CHASE_MOVES_LIMIT;

public class ClydeChasingMovePolicy extends AbstractMovePolicy {
	
	private int SOGLIA_COMPORTAMENTO_BLINKY = 8 * SharedMazeData.GRID_GAP;
	
	private PacManView pacManView;
	

	public ClydeChasingMovePolicy(PacManView pacManView, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacManView = pacManView;
	}

	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		Direction direzioneScelta = null;
		
		double distance = ghost.getGhostView().getPosition().distance(pacManView.getPosition());
		
		if (distance > SOGLIA_COMPORTAMENTO_BLINKY) {
			ComparatoreDirezione comparatoreDirezione = new ComparatoreDirezione(ghost.getGhostView().getPosition(), pacManView.getPosition());
			direzioneScelta = Collections.min(availableDirections, comparatoreDirezione);
		}
		else {
			Collections.shuffle(availableDirections);
			direzioneScelta = availableDirections.get(0);
		}
		return direzioneScelta;
	}

}
