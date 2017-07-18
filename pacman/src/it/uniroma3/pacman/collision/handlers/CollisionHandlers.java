package it.uniroma3.pacman.collision.handlers;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.Teleport;

public class CollisionHandlers {
	
	public void handle(PacMan p, Ghost g) {
		System.out.println("pacman-ghost");
	}
	
	public void handle(PacMan pacman, Dot dot) {
		System.out.println("Pacman-dot");
	}
	
	public void handle(PacMan p, Teleport t) {
		System.out.println("Pacman-Teleport");
	}
}
