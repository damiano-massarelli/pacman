package it.uniroma3.pacman.collision;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.movingObjects.MovingObject;

/**
 * Questo trigger viene usato per scatenare la collisione tra un punto di 
 * teletrasporto e PacMan.
 * 
 * @author damiano
 */
public class TeleportCollisionTrigger implements CollisionTrigger {
	
	private List<MovingObject> movingObjets;
	private MovingObject lastCollided;
	
	public TeleportCollisionTrigger(PacMan pacman, List<Ghost> ghosts) {
		movingObjets = new ArrayList<>();
		for (Ghost g : ghosts)
			movingObjets.add(g.getGhostView());
		movingObjets.add(pacman.getPacmanView());
	}

	@Override
	public boolean collisionOccurred() {
		for (MovingObject mo : movingObjets) {
			if (SharedMazeData.getTeleportForPosition(mo.getX(), mo.getY()) != null) {
				lastCollided = mo;
				return true;
			}
		}
		return false;
	}

	@Override
	public Object getFirst() {
		return lastCollided;
	}

	@Override
	public Object getSecond() {
		return SharedMazeData.getTeleportForPosition(lastCollided.getX(), lastCollided.getY());
	}

}
