package it.uniroma3.pacman.collision.handlers;

import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.staticObjects.Teleport;
import it.uniroma3.pacman.movingObjects.AnimatedView;

public class MovingObjectTeleportCollisionHandler implements CollisionHandler {

	@Override
	public void handle(Object o1, Object o2) {
		AnimatedView movingObject = (AnimatedView) o1;
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
