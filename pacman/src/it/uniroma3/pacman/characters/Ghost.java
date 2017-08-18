package it.uniroma3.pacman.characters;

import java.util.List;

import it.uniroma3.pacman.characters.behaviours.FrightenedMovePolicy;
import it.uniroma3.pacman.characters.behaviours.MovePolicy;
import it.uniroma3.pacman.collision.CollidableModelEntity;
import it.uniroma3.pacman.collision.CollisionHandler;
import it.uniroma3.pacman.graphics.characters.GhostView;
import it.uniroma3.pacman.graphics.characters.OnTurnListener;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.OnMoveListener;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.pacman.staticObjects.Teleport;

public class Ghost implements OnTurnListener, OnMoveListener, CollidableModelEntity {
	
	private MovePolicy movePolicy;
	
	private GhostView ghostView;
	

	public Ghost(String name, MovePolicy movePolicy, int x, int y) {
		ghostView = new GhostView(name, x, y);
		ghostView.setOnTurnListener(this);
		ghostView.addOnMoveListener(this);
		
		this.movePolicy = movePolicy;
	}
	

	@Override
	public void onTurn(List<Direction> availableDirections) {
		Direction direzioneScelta = availableDirections.get(0);
		
		direzioneScelta = movePolicy.makeDecision(this, availableDirections);
		
		ghostView.setDirection(direzioneScelta);
	}


	public void changeToFrightened() {
		this.movePolicy = new FrightenedMovePolicy(this.movePolicy, this);
		ghostView.changeToFrightened();  // Cambia aspetto grafico
	}

	@Override
	public void onMove() {
		this.movePolicy = movePolicy.nextPolicy();
	}
	
	@Override
	public GhostView getView() {
		return ghostView;
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
