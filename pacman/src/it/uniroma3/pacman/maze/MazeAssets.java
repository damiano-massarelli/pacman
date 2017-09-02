package it.uniroma3.pacman.maze;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.Teleport;
import javafx.geometry.Point2D;

public class MazeAssets {
	private List<Teleport> teleports;
	private List<Dot> dots;
	
	private MazeBlockMatrix blockMatrix;
	
	public MazeAssets() {
		blockMatrix = new MazeBlockMatrix();
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
	
	public Point2D getMazeTopLeftCorner() {
		return Point2D.ZERO;
	}
	
	public Point2D getMazeBottomLeftCorner() {
		return new Point2D(0, blockMatrix.getHeight() * MazeConstants.GRID_GAP);
	}
	
	public Point2D getMazeTopRightCorner() {
		return new Point2D(blockMatrix.getWidth() * MazeConstants.GRID_GAP, 0);
	}
	
	public Point2D getMazeBottomRightCorner() {
		return new Point2D(blockMatrix.getWidth() * MazeConstants.GRID_GAP, blockMatrix.getHeight() * MazeConstants.GRID_GAP);
	}
	
}
