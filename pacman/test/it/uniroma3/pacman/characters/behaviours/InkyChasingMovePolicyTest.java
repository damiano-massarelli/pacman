package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.maze.MazeConstants.GRID_SIZE;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.characters.GhostSprite;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class InkyChasingMovePolicyTest {
	
	private MovePolicy policy;

	@Before
	public void setUp() throws Exception {
		PacManSprite pacManSprite = new PacManSprite(null);
		
		pacManSprite.setPosition(new Point2D(-2 * GRID_SIZE, 0));
		pacManSprite.setDirection(Direction.RIGHT);
		
		GhostSprite blinkySprite = new GhostSprite("blinky", 2 * GRID_SIZE, 1 * GRID_SIZE, null);

		
		this.policy = new InkyChasingMovePolicy(blinkySprite, pacManSprite);
	}

	public void testMakeDecision() {
		Point2D ghostPos = Point2D.ZERO;
		Direction choosen = this.policy.makeDecision(ghostPos, Arrays.asList(Direction.values()));
		assertSame(Direction.LEFT, choosen);
	}


}
