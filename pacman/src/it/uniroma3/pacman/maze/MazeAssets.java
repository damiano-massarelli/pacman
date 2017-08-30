package it.uniroma3.pacman.maze;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.Teleport;

public class MazeAssets {
	private List<Teleport> teleports;
	private List<Dot> dots;
	
	private MazeBlockMatrix blockMatrix;
	
	public MazeAssets() {
		teleports = new ArrayList<>();
		dots = new ArrayList<>();
	}
	
	public void addTeleport(Teleport teleport) {
		teleports.add(teleport);
	}
	
	public void addDot(Dot dot) {
		dots.add(dot);
	}

	public List<Teleport> getTeleports() {
		return teleports;
	}

	public List<Dot> getDots() {
		return dots;
	}
	
	public MazeBlockMatrix getBlockMatrix() {
		return blockMatrix;
	}
}
