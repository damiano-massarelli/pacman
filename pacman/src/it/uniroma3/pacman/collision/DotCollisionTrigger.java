package it.uniroma3.pacman.collision;

import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.pacman.PacMan;
import it.uniroma3.pacman.staticObjects.Dot;
/**
 * {@link CollisionTrigger} per individuare collisioni tra PacMan e un
 * Dot (sia normale che magico)
 * @author damiano
 *
 */
public class DotCollisionTrigger implements CollisionTrigger {
	
	private PacMan pacMan;
	
	public DotCollisionTrigger(PacMan pacMan) {
		this.pacMan = pacMan;
	}

	@Override
	public boolean collisionOccurred() {
		Dot dot = SharedMazeData.getDotForPosition(pacMan.getX(), pacMan.getY());
		return (dot != null && !dot.isEaten());
	}

	@Override
	public Object getFirst() {
		return pacMan;
	}

	@Override
	public Object getSecond() {
		return SharedMazeData.getDotForPosition(pacMan.getX(), pacMan.getY());
	}

}
