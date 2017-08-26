package it.uniroma3.pacman.staticObjects;


import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.collision.CollidableModelEntity;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.Sprite;
import it.uniroma3.pacman.graphics.staticObjects.DotSprite;

public class Dot implements CollidableModelEntity {
	
	boolean eaten;
	private DotSprite dotSprite;
	
	public Dot(int x, int y) {
		this(new DotSprite(x, y));
	}
	
	public Dot(DotSprite dotView) {
		eaten = false;
		this.dotSprite = dotView;
	}
	
	
	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		if (eaten)
			getSprite().setVisible(false);
		else
			getSprite().setVisible(true);
		this.eaten = eaten;
	}

	@Override
	public Sprite getSprite() {
		return this.dotSprite;
	}

	@Override
	public void accept(CollisionHandler visitor, CollidableModelEntity other) {
		other.collidedWith(this, visitor);
		
	}

	@Override
	public void collidedWith(PacMan pacMan, CollisionHandler visitor) {
		visitor.handle(pacMan, this);
		
	}

	@Override
	public void collidedWith(Ghost ghost, CollisionHandler visitor) {
		// we don't care
		
	}

	@Override
	public void collidedWith(Dot dot, CollisionHandler visitor) {
		// impossible
		
	}

	@Override
	public void collidedWith(MagicDot dot, CollisionHandler visitor) {
		// nope
		
	}

	@Override
	public void collidedWith(Teleport teleport, CollisionHandler visitor) {
		// nah
	}
	
	
}
