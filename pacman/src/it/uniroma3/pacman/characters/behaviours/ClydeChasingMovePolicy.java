package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.maze.MazeConstants;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class ClydeChasingMovePolicy extends AbstractMovePolicy {
	
	private int SOGLIA_COMPORTAMENTO_BLINKY = 8 * MazeConstants.GRID_GAP;
	
	private PacManSprite pacManSprite;
	

	public ClydeChasingMovePolicy(PacManSprite pacManView, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacManSprite = pacManView;
	}

	@Override
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections) {
		Direction direzioneScelta = null;
		
		double distance = ghostPosition.distance(pacManSprite.getPosition());
		
		if (distance > SOGLIA_COMPORTAMENTO_BLINKY) {
			ComparatoreDirezione comparatoreDirezione = new ComparatoreDirezione(ghostPosition, pacManSprite.getPosition());
			direzioneScelta = Collections.min(availableDirections, comparatoreDirezione);
		}
		else {
			Collections.shuffle(availableDirections);
			direzioneScelta = availableDirections.get(0);
		}
		return direzioneScelta;
	}

}
