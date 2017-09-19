package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.CHASE_MOVES_LIMIT;

import java.util.Collections;
import java.util.List;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class PinkyChasingMovePolicy extends AbstractMovePolicy {

	private PacManSprite pacManSprite;
	
	public PinkyChasingMovePolicy(PacManSprite pacManSprite) {
		super(CHASE_MOVES_LIMIT);
		this.pacManSprite = pacManSprite;
	}

	@Override
	public Direction makeDecision(Point2D ghostPosition, List<Direction> availableDirections) {
		
		Point2D targetPos = new Point2D(pacManSprite.getX() + pacManSprite.getDirection().getDeltaX() * 4, 
				pacManSprite.getY() + pacManSprite.getDirection().getDeltaY() * 4);
		
		ComparatoreDirezione comparatore = new ComparatoreDirezione(ghostPosition, targetPos);
		return Collections.min(availableDirections, comparatore);
	}

}
