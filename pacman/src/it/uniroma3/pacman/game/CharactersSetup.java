package it.uniroma3.pacman.game;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.characters.behaviours.ScatteringMovePolicy;
import it.uniroma3.pacman.characters.behaviours.ChasingMovePolicy;
import it.uniroma3.pacman.characters.behaviours.MovePolicy;
import it.uniroma3.pacman.maze.MazeAssets;
import it.uniroma3.pacman.maze.MazeBlockMatrix;
import javafx.geometry.Point2D;


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
		
		Point2D blinkyScatterPoint = mazeAssets.getMazeTopRightCorner();
		Point2D pinkyScatterPoint = mazeAssets.getMazeTopLeftCorner();
		Point2D inkyScatterPoint = mazeAssets.getMazeBottomRightCorner();
		Point2D clydeScatterPoint = mazeAssets.getMazeBottomLeftCorner();
	
		
		MovePolicy blinkyScatteringPolicy = new ScatteringMovePolicy(blinkyScatterPoint);
		MovePolicy blinkyChasingPolicy = new ChasingMovePolicy(pacMan.getSprite(), null, clydeScatterPoint);
		blinkyScatteringPolicy.setNextPolicy(blinkyChasingPolicy);
		blinkyChasingPolicy.setNextPolicy(blinkyScatteringPolicy);
		
		Ghost ghostBlinky = new Ghost(
				"blinky",
				blinkyScatteringPolicy, 
				256,
				192,
				blockMatrix);
		game.addGhost(ghostBlinky);

		
		MovePolicy pinkyScatteringPolicy = new ScatteringMovePolicy(pinkyScatterPoint);
		MovePolicy pinkyChasingPolicy = new ChasingMovePolicy(pacMan.getSprite(), null, clydeScatterPoint);
		pinkyScatteringPolicy.setNextPolicy(pinkyChasingPolicy);
		pinkyChasingPolicy.setNextPolicy(pinkyScatteringPolicy);
		
		Ghost ghostPinky = new Ghost(
				"pinky",
				pinkyScatteringPolicy,
				256,
				240,
				blockMatrix);
		game.addGhost(ghostPinky);
		
		MovePolicy inkyScatteringPolicy = new ScatteringMovePolicy(inkyScatterPoint);
		MovePolicy inkyChasingMovePolicy = new ChasingMovePolicy(pacMan.getSprite(), ghostBlinky.getSprite(), clydeScatterPoint);
		inkyScatteringPolicy.setNextPolicy(inkyChasingMovePolicy);
		inkyChasingMovePolicy.setNextPolicy(inkyScatteringPolicy);

		Ghost ghostInky = new Ghost(
				"inky",
				inkyScatteringPolicy,
				224,
				240,
				blockMatrix);
		game.addGhost(ghostInky);

		MovePolicy clydeScatteringPolicy = new ScatteringMovePolicy(clydeScatterPoint);
		MovePolicy clydeChasingPolicy = new ChasingMovePolicy(pacMan.getSprite(), null, clydeScatterPoint);
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
