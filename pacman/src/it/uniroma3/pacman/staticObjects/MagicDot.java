package it.uniroma3.pacman.staticObjects;

import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.collision.CollidableModelEntity;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.staticObjects.MagicDotSprite;

public class MagicDot extends Dot {

	public MagicDot(int x, int y) {
		super(new MagicDotSprite(x, y));
	}

	@Override
	public void accept(CollisionHandler visitor, CollidableModelEntity other) {
		other.collidedWith(this, visitor);
	}

	@Override
	public void collidedWith(PacMan pacMan, CollisionHandler visitor) {
		visitor.handle(pacMan, this);
	}
	
	
	
}
