package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class PinkyChasingMovePolicy extends AbstractMovePolicy {

	private PacManSprite pacManView;
	
	public PinkyChasingMovePolicy(PacManSprite pacManView, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacManView = pacManView;
	}

	@Override
	public Direction makeDecision(Ghost ghost, List<Direction> availableDirections) {
		
		Point2D targetPos = new Point2D(pacManView.getX() + pacManView.getDirection().getDeltaX() * 4, 
				pacManView.getY() + pacManView.getDirection().getDeltaY() * 4);
		
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghost.getSprite().getPosition(), targetPos);
		return Collections.min(availableDirections, comparatore);
	}

}
