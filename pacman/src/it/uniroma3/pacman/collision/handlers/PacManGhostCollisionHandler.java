package it.uniroma3.pacman.collision.handlers;

import java.util.List;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.game.PacmanGame;
import it.uniroma3.pacman.ui.ScoreText;

public class PacManGhostCollisionHandler implements CollisionHandler {

	private List<Ghost> ghosts;
	private PacmanGame game;
	private int ghostEatenMultiplier;

	public PacManGhostCollisionHandler(List<Ghost> ghosts, PacmanGame game) {
		this.ghosts = ghosts;
		this.game = game;
		this.ghostEatenMultiplier = 1;
	}


	@Override
	public void handle(Object o1, Object o2) {
		PacMan pacman = (PacMan) o1;
		Ghost ghost = (Ghost) o2;
		
		if (ghost.isFrightened()) {
			ghost.resetPosition();
			ghost.resetStatus();
			int addScore =  (int) Math.pow(2, ghostEatenMultiplier) * 100;
			
			pacman.setScore(pacman.getScore() + addScore);
			
			ghostEatenMultiplier++;
			ScoreText score = new ScoreText(""+addScore, game.getGameField(), pacman.getPacmanView().getX() - 10, pacman.getPacmanView().getY());
			game.getGameField().getChildren().add(score);
		}
		
		else {
			pacman.setLives(pacman.getLives() - 1);
			
			pacman.die(game);
			pacman.getPacmanView().stop();
			for (Ghost g : ghosts) {
				g.hide();
				g.resetPosition();
			}	
		}
	}
	
	public void resetGhostEatenMultiplier() {
		this.ghostEatenMultiplier = 1;
	}
}
