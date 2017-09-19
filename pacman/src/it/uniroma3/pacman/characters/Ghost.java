package it.uniroma3.pacman.characters;

import java.util.List;

import it.uniroma3.pacman.characters.behaviours.FrightenedMovePolicy;
import it.uniroma3.pacman.characters.behaviours.MovePolicy;
import it.uniroma3.pacman.collision.CollidableModelEntity;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.characters.GhostSprite;
import it.uniroma3.pacman.graphics.characters.OnTurnListener;
import it.uniroma3.pacman.maze.MazeBlockMatrix;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.pacman.staticObjects.Teleport;
import static it.uniroma3.pacman.characters.behaviours.PolicyConsts.FALSING_FRIGHTENED_MOVES_THRESHOLD;

public class Ghost implements OnTurnListener, OnMoveListener, CollidableModelEntity {
	
	private MovePolicy movePolicy;
	
	private GhostSprite ghostSprite;
	
	private MovePolicy initialMovePolicy;
	
	public Ghost(String name, MovePolicy movePolicy, int x, int y, MazeBlockMatrix blockMatrix) {
		ghostSprite = new GhostSprite(name, x, y, blockMatrix);
		ghostSprite.setOnTurnListener(this);
		ghostSprite.addOnMoveListener(this);
		this.initialMovePolicy = movePolicy;
		this.movePolicy = movePolicy;
	}
	

	@Override
	public void onTurn(List<Direction> availableDirections) {
		Direction direzioneScelta = availableDirections.get(0);
		
		direzioneScelta = movePolicy.makeDecision(this.ghostSprite.getPosition(), availableDirections);
		
		ghostSprite.setDirection(direzioneScelta);
	}


	public void changeToFrightened() {
		MovePolicy frightenedPolicy = new FrightenedMovePolicy();
		frightenedPolicy.setNextPolicy(this.movePolicy);
		this.movePolicy = frightenedPolicy;
		ghostSprite.changeToFrightened();  // Cambia aspetto grafico
	}

	public boolean isFrightened() {
		return this.movePolicy.getClass() == FrightenedMovePolicy.class;
	}
	
	
	@Override
	public void onMove() {
		if (this.movePolicy.hasNext()) {
			this.movePolicy = this.movePolicy.next();
			if (this.movePolicy.getClass() != FrightenedMovePolicy.class)
				this.ghostSprite.changeToNormal();
		}
		
		if (this.movePolicy.getClass() == FrightenedMovePolicy.class 
				&& this.movePolicy.getRemainingMovesToNextPolicy() == FALSING_FRIGHTENED_MOVES_THRESHOLD)
			this.ghostSprite.changeToFlashingFrightened();
	}
	
	@Override
	public GhostSprite getSprite() {
		return ghostSprite;
	}

	public void reset() {
		this.movePolicy = initialMovePolicy;
		this.getSprite().resetStatus();
	}

	@Override
	public void accept(CollisionHandler visitor, CollidableModelEntity other) {
		other.collidedWith(this, visitor);
		
	}


	@Override
	public void collidedWith(PacMan pacMan, CollisionHandler visitor) {
		visitor.handle(pacMan, this);
		
	}


	@Override
	public void collidedWith(Dot dot, CollisionHandler visitor) {
		// we don't care
		
	}


	@Override
	public void collidedWith(MagicDot dot, CollisionHandler visitor) {
		// we don't care
		
	}


	@Override
	public void collidedWith(Teleport teleport, CollisionHandler visitor) {
		visitor.handle(this, teleport);
		
	}


	@Override
	public void collidedWith(Ghost ghost, CollisionHandler visitor) {
		// not supported yet
		
	}

}
