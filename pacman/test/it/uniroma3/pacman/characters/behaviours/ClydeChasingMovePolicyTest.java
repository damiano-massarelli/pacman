package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.maze.MazeConstants.GRID_SIZE;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class ClydeChasingMovePolicyTest {

	private MovePolicy policy;
	
	@Before
	public void setUp() throws Exception {
		PacManSprite pacManSprite = new PacManSprite(null);
		pacManSprite.setPosition(Point2D.ZERO);
		this.policy = new ClydeChasingMovePolicy(pacManSprite, new Point2D(0, 10 * GRID_SIZE));
	}

	@Test
	public void testMakeDecisionFarFromPacMan() {
		Point2D ghostPos = new Point2D(0, 9 * GRID_SIZE);
		Direction choosen = this.policy.makeDecision(ghostPos, Arrays.asList(Direction.values()));
		assertSame(Direction.UP, choosen);
	}
	
	@Test
	public void testMakeDecisionNearPacMan() {
		Point2D ghostPos = new Point2D(0, 1 * GRID_SIZE);
		Direction choosen = this.policy.makeDecision(ghostPos, Arrays.asList(Direction.values()));
		assertSame(Direction.DOWN, choosen);
	}

}
