package it.uniroma3.pacman.game;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.characters.behaviours.BlinkyChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.ClydeChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.InkyChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.MovePolicy;
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
	
		
		MovePolicy blinkyScatteringPolicy = new ScatteringMovePolicy(mazeAssets.getMazeTopRightCorner());
		MovePolicy blinkyChasingPolicy = new BlinkyChasingMovePolicy(pacMan.getSprite());
		blinkyScatteringPolicy.setNextPolicy(blinkyChasingPolicy);
		blinkyChasingPolicy.setNextPolicy(blinkyScatteringPolicy);
		
		Ghost ghostBlinky = new Ghost(
				"blinky",
				blinkyScatteringPolicy, 
				256,
				192,
				blockMatrix);
		game.addGhost(ghostBlinky);

		
		MovePolicy pinkyScatteringPolicy = new ScatteringMovePolicy(mazeAssets.getMazeTopLeftCorner());
		MovePolicy pinkyChasingPolicy = new PinkyChasingMovePolicy(pacMan.getSprite());
		pinkyScatteringPolicy.setNextPolicy(pinkyChasingPolicy);
		pinkyChasingPolicy.setNextPolicy(pinkyScatteringPolicy);
		
		Ghost ghostPinky = new Ghost(
				"pinky",
				pinkyScatteringPolicy,
				256,
				240,
				blockMatrix);
		game.addGhost(ghostPinky);
		
		MovePolicy inkyScatteringPolicy = new ScatteringMovePolicy(mazeAssets.getMazeBottomRightCorner());
		MovePolicy inkyChasingPolicy = new InkyChasingMovePolicy(ghostBlinky.getSprite(), pacMan.getSprite());
		inkyScatteringPolicy.setNextPolicy(inkyChasingPolicy);
		inkyChasingPolicy.setNextPolicy(inkyScatteringPolicy);

		Ghost ghostInky = new Ghost(
				"inky",
				inkyScatteringPolicy,
				224,
				240,
				blockMatrix);
		game.addGhost(ghostInky);

		MovePolicy clydeScatteringPolicy = new ScatteringMovePolicy(mazeAssets.getMazeBottomLeftCorner());
		MovePolicy clydeChasingPolicy = new ClydeChasingMovePolicy(pacMan.getSprite(), mazeAssets.getMazeBottomLeftCorner());
		clydeScatteringPolicy.setNextPolicy(clydeChasingPolicy);
		clydeChasingPolicy.setNextPolicy(clydeScatteringPolicy);
		
		Ghost ghostClyde = new Ghost(
				"clyde",
				clydeScatteringPolicy,
				288,
				240,
				blockMatrix);
		game.addGhost(ghostClyde);
	}
}
