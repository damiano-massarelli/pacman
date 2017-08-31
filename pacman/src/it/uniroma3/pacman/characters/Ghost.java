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

public class Ghost implements OnTurnListener, OnMoveListener, CollidableModelEntity {
	
	private MovePolicy movePolicy;
	
	private GhostSprite ghostSprite;
	

	public Ghost(String name, MovePolicy movePolicy, int x, int y, MazeBlockMatrix blockMatrix) {
		ghostSprite = new GhostSprite(name, x, y, blockMatrix);
		ghostSprite.setOnTurnListener(this);
		ghostSprite.addOnMoveListener(this);
		
		this.movePolicy = movePolicy;
	}
	

	@Override
	public void onTurn(List<Direction> availableDirections) {
		Direction direzioneScelta = availableDirections.get(0);
		
		direzioneScelta = movePolicy.makeDecision(this, availableDirections);
		
		ghostSprite.setDirection(direzioneScelta);
	}


	public void changeToFrightened() {
		this.movePolicy = new FrightenedMovePolicy(this.movePolicy, this);
		ghostSprite.changeToFrightened();  // Cambia aspetto grafico
	}

	public boolean isFrightened() {
		return getSprite().isUsingFrightenedImages();
	}
	
	
	@Override
	public void onMove() {
		/*
		 * TODO: change movePolicy to implement an iterable interface, if next() is FrightenedMovePolicy.class
		 * then change to hollow ghost, when movePolicy is FrightenedMovePolicy.class and remainingTime is equal
		 * to something (say 7) then switch to flashing. If next() is not equal to FrightenedMovePolicy.class then
		 * change to normal. I'm a bit concerned it may be difficult to understand but as long as it is simple it
		 * make sense to have this code here
		 */
		this.movePolicy = movePolicy.nextPolicy();
	}
	
	@Override
	public GhostSprite getSprite() {
		return ghostSprite;
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
