package it.uniroma3.pacman.staticObjects;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.collision.CollidableModelEntity;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.staticObjects.TeleportSprite;

public class Teleport implements CollidableModelEntity {
	private Teleport nextTeleport;
	
	private TeleportSprite teleportSprite;

	public Teleport(int x, int y) {
		teleportSprite = new TeleportSprite(x, y);
	}

	public Teleport getNextTeleport() {
		return nextTeleport;
	}

	public void setNextTeleport(Teleport nextTeleport) {
		this.nextTeleport = nextTeleport;
	}

	@Override
	public TeleportSprite getSprite() {
		return teleportSprite;
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
		visitor.handle(ghost, this);
		
	}

	@Override
	public void collidedWith(Dot dot, CollisionHandler visitor) {
		// impossible
		
	}

	@Override
	public void collidedWith(MagicDot dot, CollisionHandler visitor) {
		// impossible
		
	}

	@Override
	public void collidedWith(Teleport teleport, CollisionHandler visitor) {
		// i don't think it will happen
		
	}
	
}
