package it.uniroma3.pacman.characterGraphics;

import java.util.List;

import it.uniroma3.pacman.movingObjects.Direction;

public interface OnTurnListener {
	public void onTurn(List<Direction> availableDirections);
}