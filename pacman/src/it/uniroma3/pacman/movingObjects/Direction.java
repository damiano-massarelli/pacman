package it.uniroma3.pacman.movingObjects;

import it.uniroma3.pacman.maze.SharedMazeData;

public class Direction {
	private int dx;
	private int dy;
	
	public Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public void setDxDy(int dx, int dy) {
		setDx(dx);
		setDy(dy);
		
	}
	
	
	public int getDeltaX() {
		return SharedMazeData.GRID_GAP * this.dx;
	}
	
	public int getDeltaY() {
		return SharedMazeData.GRID_GAP * this.dy;
	}
	
	public Direction getInverse() {
		return new Direction(-this.getDx(), -this.getDy());
	}
	
	public boolean equals(Object dir) {
		if (dir == null) return false;
		Direction direction = (Direction) dir;
		return this.getDx() == direction.getDx() && this.getDy() == direction.getDy();
	}
	
}


