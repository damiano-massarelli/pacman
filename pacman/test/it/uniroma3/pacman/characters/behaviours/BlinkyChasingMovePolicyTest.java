package it.uniroma3.pacman.characters.behaviours;

import static it.uniroma3.pacman.maze.MazeConstants.GRID_SIZE;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.pacman.graphics.characters.PacManSprite;
import it.uniroma3.pacman.movingObjects.Direction;
import javafx.geometry.Point2D;

public class BlinkyChasingMovePolicyTest {

	private BlinkyChasingMovePolicy policy;
	
	@Before
	public void setUp() throws Exception {
        PacManSprite pacManSprite = new PacManSprite(null);
		pacManSprite.setPosition(Point2D.ZERO);
		
		policy = new BlinkyChasingMovePolicy(pacManSprite);
	}

	@Test
	public void testMakeDecisionAllDirectionsGhostAbove() {
		/* Il sistema di riferimento usato da javafx prevede che l'asse y scende verso
		 * il basso e l'orine è il vertice in alto a sinistra della finestra
		 */
		Point2D ghostPos = new Point2D(0, -1 * GRID_SIZE);
		Direction choosen = this.policy.makeDecision(ghostPos, Arrays.asList(Direction.values()));
		assertSame(Direction.DOWN, choosen);
	}
	
	@Test
	public void testMakeDecisionBestDirectionAbsent() {
		/* La migliore dovrebbe essere LEFT, ma non è presente */
		Point2D ghostPos = new Point2D(3 * GRID_SIZE, 2 * GRID_SIZE);
		Direction choosen = this.policy.makeDecision(ghostPos, Arrays.asList(Direction.DOWN, Direction.UP, Direction.RIGHT));
		assertSame(Direction.UP, choosen);
	}

}
