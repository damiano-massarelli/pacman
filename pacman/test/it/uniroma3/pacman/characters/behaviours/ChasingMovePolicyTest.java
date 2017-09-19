package it.uniroma3.pacman.characters.behaviours;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.characters.GhostSprite;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;
import static it.uniroma3.pacman.maze.MazeConstants.GRID_SIZE;

public class ChasingMovePolicyTest {
	
	private ChasingMovePolicy chasingPolicy;
	private PacManSprite pacManSprite;

	@Before
	public void setUp() throws Exception {
		pacManSprite = new PacManSprite(null);
		
		pacManSprite.setPosition(Point2D.ZERO);
		pacManSprite.setDirection(Direction.RIGHT);
		
		GhostSprite blinkySprite = new GhostSprite("blinky", 2 * GRID_SIZE, 1 * GRID_SIZE, null);
		this.chasingPolicy = new ChasingMovePolicy(pacManSprite, blinkySprite, new Point2D(0, 10 * GRID_SIZE));
	}

	/* BLINKY */
	@Test
	public void testBlinkyAllDirectionsGhostAbove() {
		/* Il sistema di riferimento usato da javafx prevede che l'asse y scende verso
		 * il basso e l'orine è il vertice in alto a sinistra della finestra
		 */
		Ghost blinky = new Ghost("blinky", null, 0, -1 * GRID_SIZE, null);
		Direction choosen = this.chasingPolicy.makeDecision(blinky, Arrays.asList(Direction.values()));
		assertSame(Direction.DOWN, choosen);
	}
	
	@Test
	public void testBlinkyBestDirectionAbsent() {
		/* La migliore dovrebbe essere LEFT, ma non è presente */
		Ghost blinky = new Ghost("blinky", null, 3 * GRID_SIZE, 2 * GRID_SIZE, null);
		Direction choosen = this.chasingPolicy.makeDecision(blinky, Arrays.asList(Direction.DOWN, Direction.UP, Direction.RIGHT));
		assertSame(Direction.UP, choosen);
	}
	
	/* CLYDE */
	@Test
	public void testClydeFarFromPacMan() {
		Ghost clyde = new Ghost("clyde", null, 0, 9 * GRID_SIZE, null);
		Direction choosen = this.chasingPolicy.makeDecision(clyde, Arrays.asList(Direction.values()));
		assertSame(Direction.UP, choosen);
	}
	
	@Test
	public void testClydeNearPacMan() {
		Ghost clyde = new Ghost("clyde", null, 0, 1 * GRID_SIZE, null);
		Direction choosen = this.chasingPolicy.makeDecision(clyde, Arrays.asList(Direction.values()));
		assertSame(Direction.DOWN, choosen);
	}
	
	/* PINKY */
	@Test
	public void testPinky() {
		Ghost clyde = new Ghost("pinky", null, 0, 1 * GRID_SIZE, null);
		Direction choosen = this.chasingPolicy.makeDecision(clyde, Arrays.asList(Direction.values()));
		assertSame(Direction.RIGHT, choosen);
	}
	
	/* INKY */
	@Test
	public void testInky() {
		pacManSprite.setPosition(new Point2D(-2 * GRID_SIZE, 0));
		Ghost inky = new Ghost("inky", null, 0, 0, null);
		Direction choosen = this.chasingPolicy.makeDecision(inky, Arrays.asList(Direction.values()));
		assertSame(Direction.LEFT, choosen);
	}

}
