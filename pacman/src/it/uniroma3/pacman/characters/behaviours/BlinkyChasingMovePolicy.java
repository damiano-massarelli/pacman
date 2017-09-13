package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.PolicyConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;

import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class BlinkyChasingMovePolicy extends AbstractMovePolicy {
	
	private PacManSprite pacManSprite;
	
	public BlinkyChasingMovePolicy(PacManSprite pacManSprite, MovePolicy nextPolicy) {
		super(nextPolicy, CHASE_MOVES_LIMIT);
		this.pacManSprite = pacManSprite;
	}


	@Override
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections) {
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghostPosition, pacManSprite.getPosition());
		return Collections.min(availableDirections, comparatore); // c'e' sempre almeno una direzione
	}


}
