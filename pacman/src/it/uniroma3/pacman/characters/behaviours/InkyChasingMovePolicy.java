package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class InkyChasingMovePolicy extends AbstractMovePolicy {
	
	private Ghost blinky;
	private PacManSprite pacManView;
	

	public InkyChasingMovePolicy(Ghost blinky, PacManSprite pacManView, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.blinky = blinky;
		this.pacManView = pacManView;
	}


	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		int pacmanFacingX = pacManView.getX() + pacManView.getDirection().getDeltaX() * 2;
		int pacmanFacingY = pacManView.getY() + pacManView.getDirection().getDeltaY() * 2;
		
		int deltaX = (pacmanFacingX - blinky.getSprite().getX()) * 2;
		int deltaY = (pacmanFacingY - blinky.getSprite().getY()) * 2;
		
		Point2D targetPos = new Point2D(blinky.getSprite().getX() + deltaX, blinky.getSprite().getY() + deltaY);
		
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getSprite().getPosition(), targetPos);
		
		return Collections.min(availableDirections, comparatore);
	}

}
