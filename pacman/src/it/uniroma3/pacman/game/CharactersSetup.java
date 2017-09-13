package it.uniroma3.pacman.game;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.characters.behaviours.ScatteringMovePolicy;
import it.uniroma3.pacman.characters.behaviours.ChasingMovePolicy;
import it.uniroma3.pacman.maze.MazeAssets;
import it.uniroma3.pacman.maze.MazeBlockMatrix;


/**
 * 
 * Classe utilizzata da {@link PacmanGame} per creare i personaggi del gioco
 *
 */
public class CharactersSetup {
	
	public void setup(PacmanGame game) {
		MazeAssets mazeAssets = game.getMazeAssets();
		MazeBlockMatrix blockMatrix = mazeAssets.getBlockMatrix();
		
		PacMan pacMan = new PacMan(blockMatrix);
		game.setPacMan(pacMan);
	
		
		ScatteringMovePolicy blinkyMovePolicy = new ScatteringMovePolicy(null, mazeAssets.getMazeTopRightCorner());
		blinkyMovePolicy.setNextPolicy(new ChasingMovePolicy(pacMan.getSprite(), blinkyMovePolicy, null));
		
		Ghost ghostBlinky = new Ghost(
				"blinky",
				blinkyMovePolicy, 
				256,
				192,
				blockMatrix);
		game.addGhost(ghostBlinky);

		
		ScatteringMovePolicy pinkyMovePolicy = new ScatteringMovePolicy(null, mazeAssets.getMazeTopLeftCorner());
		pinkyMovePolicy.setNextPolicy(new ChasingMovePolicy(pacMan.getSprite(), pinkyMovePolicy, null));
		
		Ghost ghostPinky = new Ghost(
				"pinky",
				pinkyMovePolicy,
				256,
				240,
				blockMatrix);
		game.addGhost(ghostPinky);
		
		ScatteringMovePolicy inkyMovePolicy = new ScatteringMovePolicy(null, mazeAssets.getMazeBottomRightCorner());
		inkyMovePolicy.setNextPolicy(new ChasingMovePolicy(pacMan.getSprite(), inkyMovePolicy, ghostBlinky.getSprite()));

		Ghost ghostInky = new Ghost(
				"inky",
				inkyMovePolicy,
				224,
				240,
				blockMatrix);
		game.addGhost(ghostInky);

		ScatteringMovePolicy clydeMovePolicy = new ScatteringMovePolicy(null, mazeAssets.getMazeBottomLeftCorner());
		clydeMovePolicy.setNextPolicy(new ChasingMovePolicy(pacMan.getSprite(), clydeMovePolicy, null));
		
		Ghost ghostClyde = new Ghost(
				"clyde",
				clydeMovePolicy,
				288,
				240,
				blockMatrix);
		game.addGhost(ghostClyde);
	}
}
