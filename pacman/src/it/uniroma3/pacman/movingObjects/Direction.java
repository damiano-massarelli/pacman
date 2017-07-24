package it.uniroma3.pacman.movingObjects;

import it.uniroma3.pacman.maze.SharedMazeData;

public enum Direction {
	NORTH (0, -1) {

		@Override
		public Direction getInverse() {
			return Direction.SOUTH;
		}
		
	},
	
	SOUTH (0, 1) {

		@Override
		public Direction getInverse() {
			return Direction.NORTH;
		}
		
	},
	
	EST (1, 0) {

		@Override
		public Direction getInverse() {
			return OVEST;
		}
		
	},
	
	OVEST (-1, 0) {

		@Override
		public Direction getInverse() {
			return EST;
		}
		
	};
	
	private final int dirX;
	private final int dirY;
	
	private Direction(int dirX, int dirY) {
		this.dirX = dirX;
		this.dirY = dirY;
	}

	public int getDirX() {
		return dirX;
	}


	public int getDirY() {
		return dirY;
	}
	
	public int getDeltaX() {
		return SharedMazeData.GRID_GAP * this.dirX;
	}
	
	public int getDeltaY() {
		return SharedMazeData.GRID_GAP * this.dirY;
	}
	
	public abstract Direction getInverse();
		
}


