package it.uniroma3.pacman.game;

import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.characters.PacMan;
import it.uniroma3.pacman.characters.behaviours.ScatteringMovePolicy;
import it.uniroma3.pacman.characters.behaviours.ChasingMovePolicy;
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
	
		
		ScatteringMovePolicy blinkyMovePolicy = new ScatteringMovePolicy(null, blinkyScatterPoint);
		blinkyMovePolicy.setNextPolicy(new ChasingMovePolicy(pacMan.getSprite(), blinkyMovePolicy, null, clydeScatterPoint));
		
		Ghost ghostBlinky = new Ghost(
				"blinky",
				blinkyMovePolicy, 
				256,
				192,
				blockMatrix);
		game.addGhost(ghostBlinky);

		
		ScatteringMovePolicy pinkyMovePolicy = new ScatteringMovePolicy(null, pinkyScatterPoint);
		pinkyMovePolicy.setNextPolicy(new ChasingMovePolicy(pacMan.getSprite(), pinkyMovePolicy, null, clydeScatterPoint));
		
		Ghost ghostPinky = new Ghost(
				"pinky",
				pinkyMovePolicy,
				256,
				240,
				blockMatrix);
		game.addGhost(ghostPinky);
		
		ScatteringMovePolicy inkyMovePolicy = new ScatteringMovePolicy(null, inkyScatterPoint);
		inkyMovePolicy.setNextPolicy(new ChasingMovePolicy(pacMan.getSprite(), inkyMovePolicy, ghostBlinky.getSprite(), clydeScatterPoint));

		Ghost ghostInky = new Ghost(
				"inky",
				inkyMovePolicy,
				224,
				240,
				blockMatrix);
		game.addGhost(ghostInky);

		ScatteringMovePolicy clydeMovePolicy = new ScatteringMovePolicy(null, clydeScatterPoint);
		clydeMovePolicy.setNextPolicy(new ChasingMovePolicy(pacMan.getSprite(), clydeMovePolicy, null, clydeScatterPoint));
		
		Ghost ghostClyde = new Ghost(
				"clyde",
				clydeMovePolicy,
				288,
				240,
				blockMatrix);
		game.addGhost(ghostClyde);
	}
}
