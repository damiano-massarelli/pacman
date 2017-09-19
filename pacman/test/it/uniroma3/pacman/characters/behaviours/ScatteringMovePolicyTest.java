package it.uniroma3.pacman.characters.behaviours;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.pacman.characters.Ghost;
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
	public void testHasNextTimeNotExpired() {
		assertFalse(this.scatteringPolicy.hasNext());
	}
	
	@Test
	public void testNextTimeNotExpired() {
		assertSame(this.scatteringPolicy, this.scatteringPolicy.next());
	}
	
	@Test
	public void testHasNextTimeNotExpiredByOne() {
		boolean lastResult = false;;
		for (int i = 0; i < PolicyConsts.SCATTER_MOVES_LIMIT - 1; i++) 
			lastResult = this.scatteringPolicy.hasNext();
		assertFalse(lastResult);
	}
	
	@Test
	public void testHasNextTimeExpired() {
		boolean lastResult = false;
		for (int i = 0; i < PolicyConsts.SCATTER_MOVES_LIMIT; i++)
			lastResult = this.scatteringPolicy.hasNext();
		assertTrue(lastResult);
	}
	
	@Test
	public void testNextTimeExpired() {
		for (int i = 0; i < PolicyConsts.SCATTER_MOVES_LIMIT; i++)
			this.scatteringPolicy.hasNext();
		assertSame(FrightenedMovePolicy.class, this.scatteringPolicy.next().getClass());
	}
	
	@Test
	public void testMakeDecisionGhostAbove() {
		/* Il sistema di riferimento usato da javafx prevede che l'asse y scende verso
		 * il basso e l'orine è il vertice in alto a sinistra della finestra
		 */
		Ghost ghost = new Ghost("blinky", null, 0, -1 * GRID_SIZE, null);
		Direction choosen = this.scatteringPolicy.makeDecision(ghost, Arrays.asList(Direction.values()));
		assertSame(Direction.DOWN, choosen);
	}
	
	@Test
	public void testMakeDecisionGhostBelow() {
		/* Il sistema di riferimento usato da javafx prevede che l'asse y scende verso
		 * il basso e l'orine è il vertice in alto a sinistra della finestra
		 */
		Ghost ghost = new Ghost("blinky", null, 0, 1 * GRID_SIZE, null);
		Direction choosen = this.scatteringPolicy.makeDecision(ghost, Arrays.asList(Direction.values()));
		assertSame(Direction.UP, choosen);
	}
	
	@Test
	public void testMakeDecisionGhostRight() {
		/* Il sistema di riferimento usato da javafx prevede che l'asse y scende verso
		 * il basso e l'orine è il vertice in alto a sinistra della finestra
		 */
		Ghost ghost = new Ghost("blinky", null, 1 * GRID_SIZE, 0, null);
		Direction choosen = this.scatteringPolicy.makeDecision(ghost, Arrays.asList(Direction.values()));
		assertSame(Direction.LEFT, choosen);
	}
	
	@Test
	public void testMakeDecisionGhostLeft() {
		/* Il sistema di riferimento usato da javafx prevede che l'asse y scende verso
		 * il basso e l'orine è il vertice in alto a sinistra della finestra
		 */
		Ghost ghost = new Ghost("blinky", null, -1* GRID_SIZE, 0, null);
		Direction choosen = this.scatteringPolicy.makeDecision(ghost, Arrays.asList(Direction.values()));
		assertSame(Direction.RIGHT, choosen);
	}
	

}
