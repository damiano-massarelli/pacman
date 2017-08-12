package it.uniroma3.pacman.staticObjectGraphics;


public class Teleport {
	
	private Teleport nextTeleport;
	int x;
	int y;

	public Teleport(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Teleport getNextTeleport() {
		return nextTeleport;
	}

	public void setNextTeleport(Teleport nextTeleport) {
		this.nextTeleport = nextTeleport;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
