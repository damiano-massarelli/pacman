package it.uniroma3.pacman.game;

import static it.uniroma3.pacman.characters.behaviours.GhostConsts.*;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.characters.behaviours.BlinkyChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.ClydeChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.InkyChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.MovePolicy;
import it.uniroma3.pacman.characters.behaviours.PinkyChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.ScatteringMovePolicy;
import it.uniroma3.pacman.maze.MazeBlockMatrix;


/**
 * 
 * Classe utilizzata da {@link PacmanGame} per creare i personaggi del gioco
 *
 */
public class CharactersSetup {
	
	public void setup(PacmanGame game) {
		MazeBlockMatrix blockMatrix = game.getMazeAssets().getBlockMatrix();
		
		PacMan pacMan = new PacMan(blockMatrix);
		game.setPacMan(pacMan);
	
		
		MovePolicy blinkyMovePolicy = new ScatteringMovePolicy(null, TOP_RIGHT_CORNER);
		blinkyMovePolicy.setNextPolicy(new BlinkyChasingMovePolicy(pacMan.getSprite(), blinkyMovePolicy));
		
		Ghost ghostBlinky = new Ghost(
				"red",
				blinkyMovePolicy, 
				256,
				192,
				blockMatrix);
		game.addGhost(ghostBlinky);

		
		MovePolicy pinkyMovePolicy = new ScatteringMovePolicy(null, TOP_LEFT_CORNER);
		pinkyMovePolicy.setNextPolicy(new PinkyChasingMovePolicy(pacMan.getSprite(), pinkyMovePolicy));
		
		Ghost ghostPinky = new Ghost(
				"pink",
				pinkyMovePolicy,
				256,
				240,
				blockMatrix);
		game.addGhost(ghostPinky);
		
		MovePolicy inkyMovePolicy = new ScatteringMovePolicy(null, BOTTOM_RIGHT_CORNER);
		inkyMovePolicy.setNextPolicy(new InkyChasingMovePolicy(ghostBlinky, pacMan.getSprite(), inkyMovePolicy));

		Ghost ghostInky = new Ghost(
				"cyan",
				inkyMovePolicy,
				224,
				240,
				blockMatrix);
		game.addGhost(ghostInky);

		MovePolicy clydeMovePolicy = new ScatteringMovePolicy(null, BOTTOM_LEFT_CORNER);
		clydeMovePolicy.setNextPolicy(new ClydeChasingMovePolicy(pacMan.getSprite(), clydeMovePolicy));
		
		Ghost ghostClyde = new Ghost(
				"orange",
				clydeMovePolicy,
				288,
				240,
				blockMatrix);
		game.addGhost(ghostClyde);
	}
}
