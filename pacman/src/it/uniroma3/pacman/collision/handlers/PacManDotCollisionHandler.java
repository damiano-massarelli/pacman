package it.uniroma3.pacman.collision.handlers;

import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.game.PacmanGame;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.pacman.PacMan;
import it.uniroma3.pacman.staticObjects.Dot;

public class PacManDotCollisionHandler implements CollisionHandler {

	private PacmanGame game;
	
	
	public PacManDotCollisionHandler(PacmanGame gameField) {
		this.game = gameField;
	}



	@Override
	public void handle(Object o1, Object o2) {
		PacMan pacman = (PacMan) o1;
		Dot dot = (Dot) o2;
		
		dot.setEaten(true);
		pacman.setDotEatenCount(pacman.getDotEatenCount() + 1);
		pacman.setScore(pacman.getScore() + 10);
		if (pacman.getDotEatenCount() >= SharedMazeData.getDotTotal()) {
			game.levelCompleted();
		}
		
	}

}
