package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.maze.MazeConstants;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class ClydeChasingMovePolicy extends AbstractMovePolicy {
	
	private int SOGLIA_COMPORTAMENTO_BLINKY = 8 * MazeConstants.GRID_SIZE;
	
	private PacManSprite pacManSprite;
	private Point2D scatterTarget;

	public ClydeChasingMovePolicy(PacManSprite pacManView, Point2D scatterTarget) {
		super(CHASE_MOVES_LIMIT);
		this.pacManSprite = pacManView;
		this.scatterTarget = scatterTarget;
	}

	@Override
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections) {
		double distance = ghostPosition.distance(pacManSprite.getPosition());
		ComparatoreDirezione comparatoreDirezione;
		
		if (distance > SOGLIA_COMPORTAMENTO_BLINKY) 
			comparatoreDirezione = new ComparatoreDirezione(ghostPosition, pacManSprite.getPosition());
		else 
			comparatoreDirezione = new ComparatoreDirezione(ghostPosition, scatterTarget);
		
		return Collections.min(availableDirections, comparatoreDirezione);
	}

}
