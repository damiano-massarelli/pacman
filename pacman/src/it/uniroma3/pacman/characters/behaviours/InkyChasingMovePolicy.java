package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.characters.PacManView;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class InkyChasingMovePolicy extends AbstractMovePolicy {
	
	private Ghost blinky;
	private PacManView pacManView;
	

	public InkyChasingMovePolicy(Ghost blinky, PacManView pacManView, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.blinky = blinky;
		this.pacManView = pacManView;
	}


	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		int pacmanFacingX = pacManView.getX() + pacManView.getDirection().getDeltaX() * 2;
		int pacmanFacingY = pacManView.getY() + pacManView.getDirection().getDeltaY() * 2;
		
		int deltaX = (pacmanFacingX - blinky.getView().getX()) * 2;
		int deltaY = (pacmanFacingY - blinky.getView().getY()) * 2;
		
		Point2D targetPos = new Point2D(blinky.getView().getX() + deltaX, blinky.getView().getY() + deltaY);
		
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getView().getPosition(), targetPos);
		
		return Collections.min(availableDirections, comparatore);
	}

}
