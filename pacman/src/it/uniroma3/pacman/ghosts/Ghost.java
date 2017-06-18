package it.uniroma3.pacman.ghosts;

import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;

public class Ghost extends AbstractGhost {
	
	private MovePolicy movePolicy;
	

	public Ghost(String name, MovePolicy movePolicy, int x, int y) {
		super(name, x, y);
		
		this.movePolicy = movePolicy;
	}
	

	@Override
	protected void onTurn(List<Direction> availableDirections) {
		Direction direzioneScelta = availableDirections.get(0);
		
		direzioneScelta = movePolicy.makeDecision(this, availableDirections);
		
		setDirection(direzioneScelta);
	}

	@Override
	public void changeToFrightened() {
		this.movePolicy = new FrightenedMovePolicy(this.movePolicy, this);
		super.changeToFrightened();  // Cambia aspetto grafico
	}

	@Override
	protected void onMove() {
		this.movePolicy = movePolicy.nextPolicy();
	}

}
