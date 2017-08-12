package it.uniroma3.pacman.collision.handlers;

import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.staticObjectGraphics.MagicDotView;

public class PacManMagicDotCollisionHandler implements CollisionHandler {

	private List<Ghost> ghosts;
	private PacManGhostCollisionHandler pacmanGhostCollisionHandler;
	
	public PacManMagicDotCollisionHandler(List<Ghost> ghosts, PacManGhostCollisionHandler handler) {
		super();
		this.ghosts = ghosts;
		pacmanGhostCollisionHandler = handler;
	}



	@Override
	public void handle(Object o1, Object o2) {
		MagicDotView dot = (MagicDotView) o2;
		dot.setEaten(true);
		for (Ghost g : ghosts)
			g.changeToFrightened();
		
		pacmanGhostCollisionHandler.resetGhostEatenMultiplier();
	}

}
