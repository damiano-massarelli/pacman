package it.uniroma3.pacman.graphics.characters;

import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;

public interface OnTurnListener {
	public void onTurn(List<Direction> availableDirections);
}
