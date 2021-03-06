package it.uniroma3.pacman.collision;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.graphics.Sprite;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.pacman.staticObjects.Teleport;

/**
 * This interface must be implemented by <em>domain objects</em> whose sprites can collide with
 * other sprites 
 * @author damiano massarelli
 *
 */
public interface CollidableModelEntity {
	public Sprite getSprite();
	
	public void accept(CollisionHandler visitor, CollidableModelEntity other);
	
	public void collidedWith(PacMan pacMan, CollisionHandler visitor);
	
	public void collidedWith(Ghost ghost, CollisionHandler visitor);
	
	public void collidedWith(Dot dot, CollisionHandler visitor);
	
	public void collidedWith(MagicDot dot, CollisionHandler visitor);
	
	public void collidedWith(Teleport teleport, CollisionHandler visitor);
}
