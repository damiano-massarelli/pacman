package it.uniroma3.pacman.game;

import it.uniroma3.pacman.ghosts.BlinkyChasingMovePolicy;
import it.uniroma3.pacman.ghosts.ClydeChasingMovePolicy;
import it.uniroma3.pacman.ghosts.InkyChasingMovePolicy;
import it.uniroma3.pacman.ghosts.MovePolicy;
import it.uniroma3.pacman.ghosts.PinkyChasingMovePolicy;
import it.uniroma3.pacman.ghosts.ScatteringMovePolicy;
import it.uniroma3.pacman.graphics.PacManView;

import static it.uniroma3.pacman.ghosts.GhostConsts.*;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;


/**
 * 
 * Classe utilizzata da {@link PacmanGame} per creare i personaggi del gioco
 *
 */
public class CharactersSetup {
	
	public void setup(PacmanGame game) {
		PacMan pacMan = new PacMan();
		game.setPacMan(pacMan);
	
		
		MovePolicy blinkyMovePolicy = new ScatteringMovePolicy(null, TOP_RIGHT_CORNER);
		blinkyMovePolicy.setNextPolicy(new BlinkyChasingMovePolicy(pacMan.getPacmanView(), blinkyMovePolicy));
		
		Ghost ghostBlinky = new Ghost(
				"red",
				blinkyMovePolicy, 
				256,
				192);
		game.addGhost(ghostBlinky);

		
		MovePolicy pinkyMovePolicy = new ScatteringMovePolicy(null, TOP_LEFT_CORNER);
		pinkyMovePolicy.setNextPolicy(new PinkyChasingMovePolicy(pacMan.getPacmanView(), pinkyMovePolicy));
		
		Ghost ghostPinky = new Ghost(
				"pink",
				pinkyMovePolicy,
				256,
				240);
		game.addGhost(ghostPinky);
		
		MovePolicy inkyMovePolicy = new ScatteringMovePolicy(null, BOTTOM_RIGHT_CORNER);
		inkyMovePolicy.setNextPolicy(new InkyChasingMovePolicy(ghostBlinky, pacMan.getPacmanView(), inkyMovePolicy));

		Ghost ghostInky = new Ghost(
				"cyan",
				inkyMovePolicy,
				224,
				240);
		game.addGhost(ghostInky);

		MovePolicy clydeMovePolicy = new ScatteringMovePolicy(null, BOTTOM_LEFT_CORNER);
		clydeMovePolicy.setNextPolicy(new ClydeChasingMovePolicy(pacMan.getPacmanView(), clydeMovePolicy));
		
		Ghost ghostClyde = new Ghost(
				"orange",
				clydeMovePolicy,
				288,
				240);
		game.addGhost(ghostClyde);
	}
}
