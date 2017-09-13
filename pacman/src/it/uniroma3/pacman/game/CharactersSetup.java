package it.uniroma3.pacman.game;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.characters.behaviours.AbstractMovePolicy;
import it.uniroma3.pacman.characters.behaviours.BlinkyChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.ClydeChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.InkyChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.PinkyChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.ScatteringMovePolicy;
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
	
		
		AbstractMovePolicy blinkyMovePolicy = new ScatteringMovePolicy(null, mazeAssets.getMazeTopRightCorner());
		blinkyMovePolicy.setNextPolicy(new BlinkyChasingMovePolicy(pacMan.getSprite(), blinkyMovePolicy));
		
		Ghost ghostBlinky = new Ghost(
				"blinky",
				blinkyMovePolicy, 
				256,
				192,
				blockMatrix);
		game.addGhost(ghostBlinky);

		
		AbstractMovePolicy pinkyMovePolicy = new ScatteringMovePolicy(null, mazeAssets.getMazeTopLeftCorner());
		pinkyMovePolicy.setNextPolicy(new PinkyChasingMovePolicy(pacMan.getSprite(), pinkyMovePolicy));
		
		Ghost ghostPinky = new Ghost(
				"pinky",
				pinkyMovePolicy,
				256,
				240,
				blockMatrix);
		game.addGhost(ghostPinky);
		
		AbstractMovePolicy inkyMovePolicy = new ScatteringMovePolicy(null, mazeAssets.getMazeBottomRightCorner());
		inkyMovePolicy.setNextPolicy(new InkyChasingMovePolicy(ghostBlinky.getSprite(), pacMan.getSprite(), inkyMovePolicy));

		Ghost ghostInky = new Ghost(
				"inky",
				inkyMovePolicy,
				224,
				240,
				blockMatrix);
		game.addGhost(ghostInky);

		AbstractMovePolicy clydeMovePolicy = new ScatteringMovePolicy(null, mazeAssets.getMazeBottomLeftCorner());
		clydeMovePolicy.setNextPolicy(new ClydeChasingMovePolicy(pacMan.getSprite(), clydeMovePolicy));
		
		Ghost ghostClyde = new Ghost(
				"clyde",
				clydeMovePolicy,
				288,
				240,
				blockMatrix);
		game.addGhost(ghostClyde);
	}
}
