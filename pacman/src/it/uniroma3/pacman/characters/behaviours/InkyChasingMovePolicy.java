package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.graphics.characters.GhostSprite;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class InkyChasingMovePolicy extends AbstractMovePolicy {
	
	private GhostSprite blinkySprite;
	private PacManSprite pacManSprite;
	

	public InkyChasingMovePolicy(GhostSprite blinky, PacManSprite pacManSprite, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.blinkySprite = blinky;
		this.pacManSprite = pacManSprite;
	}


	@Override
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections) {
		int pacmanFacingX = pacManSprite.getX() + pacManSprite.getDirection().getDeltaX() * 2;
		int pacmanFacingY = pacManSprite.getY() + pacManSprite.getDirection().getDeltaY() * 2;
		
		int deltaX = (pacmanFacingX - blinkySprite.getX()) * 2;
		int deltaY = (pacmanFacingY - blinkySprite.getY()) * 2;
		
		Point2D targetPos = new Point2D(blinkySprite.getX() + deltaX, blinkySprite.getY() + deltaY);
		
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghostPosition, targetPos);
		
		return Collections.min(availableDirections, comparatore);
	}

}
