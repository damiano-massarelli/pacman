package it.uniroma3.pacman.characters.behaviours;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;
import static it.uniroma3.pacman.maze.MazeConstants.GRID_SIZE;

public class ScatteringMovePolicyTest {
	
	private ScatteringMovePolicy scatteringPolicy;
	

	@Before
	public void setUp() throws Exception {
		this.scatteringPolicy = new ScatteringMovePolicy(Point2D.ZERO);
		this.scatteringPolicy.setNextPolicy(new FrightenedMovePolicy());
	}

	
	@Test
	public void testMakeDecisionGhostAbove() {
		/* Il sistema di riferimento usato da javafx prevede che l'asse y scende verso
		 * il basso e l'orine è il vertice in alto a sinistra della finestra
		 */
		Point2D ghostPos = new Point2D(0, -1 * GRID_SIZE);
		Direction choosen = this.scatteringPolicy.makeDecision(ghostPos, Arrays.asList(Direction.values()));
		assertSame(Direction.DOWN, choosen);
	}
	
	@Test
	public void testMakeDecisionGhostBelow() {
		/* Il sistema di riferimento usato da javafx prevede che l'asse y scende verso
		 * il basso e l'orine è il vertice in alto a sinistra della finestra
		 */
		Point2D ghostPos = new Point2D(0, 1 * GRID_SIZE);
		Direction choosen = this.scatteringPolicy.makeDecision(ghostPos, Arrays.asList(Direction.values()));
		assertSame(Direction.UP, choosen);
	}
	
	@Test
	public void testMakeDecisionGhostRight() {
		Point2D ghostPos = new Point2D(1 * GRID_SIZE, 0);
		Direction choosen = this.scatteringPolicy.makeDecision(ghostPos, Arrays.asList(Direction.values()));
		assertSame(Direction.LEFT, choosen);
	}
	
	@Test
	public void testMakeDecisionGhostLeft() {
		Point2D ghostPos = new Point2D(-1* GRID_SIZE, 0);
		Direction choosen = this.scatteringPolicy.makeDecision(ghostPos, Arrays.asList(Direction.values()));
		assertSame(Direction.RIGHT, choosen);
	}
	

}
