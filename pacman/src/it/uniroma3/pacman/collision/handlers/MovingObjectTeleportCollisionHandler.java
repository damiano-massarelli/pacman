package it.uniroma3.pacman.collision.handlers;

import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.movingObjects.MovingObject;
import it.uniroma3.pacman.staticObjects.Teleport;

public class MovingObjectTeleportCollisionHandler implements CollisionHandler {

	@Override
	public void handle(Object o1, Object o2) {
		MovingObject movingObject = (MovingObject) o1;
		Teleport teleport = (Teleport) o2;
		movingObject.stop();
		
		int nextX = teleport.getNextTeleport().getX() + movingObject.getDirection().getDeltaX();
		int nextY = teleport.getNextTeleport().getY() + movingObject.getDirection().getDeltaY();
		
		//System.out.println(nextX + " " + nextY);
		
		movingObject.setX(nextX);
		movingObject.setY(nextY);
		movingObject.start();
	}

}
