package it.uniroma3.pacman.collision;

public interface CollisionTrigger {
	public boolean collisionOccurred();
	
	public Object getFirst();
	public Object getSecond();
}
