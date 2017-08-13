package it.uniroma3.pacman.staticObjects;


import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.collision.CollidableModelEntity;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.View;
import it.uniroma3.pacman.graphics.staticObjects.DotView;

public class Dot implements CollidableModelEntity {
	
	boolean eaten;
	private DotView dotView;
	
	public Dot(int x, int y) {
		this(new DotView(x, y));
	}
	
	public Dot(DotView dotView) {
		eaten = false;
		this.dotView = dotView;
	}
	
	
	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		if (eaten)
			getView().setVisible(false);
		else
			getView().setVisible(true);
		this.eaten = eaten;
	}

	@Override
	public View getView() {
		return this.dotView;
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
