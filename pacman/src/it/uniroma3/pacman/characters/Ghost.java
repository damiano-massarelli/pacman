package it.uniroma3.pacman.characters;

import java.util.List;

import it.uniroma3.pacman.characterGraphics.GhostView;
import it.uniroma3.pacman.characterGraphics.OnTurnListener;
import it.uniroma3.pacman.ghosts.FrightenedMovePolicy;
import it.uniroma3.pacman.ghosts.MovePolicy;
import it.uniroma3.pacman.graphics.CollidableModelEntity;
import it.uniroma3.pacman.movingObjects.Direction;
import it.uniroma3.pacman.movingObjects.OnMoveListener;

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

}
