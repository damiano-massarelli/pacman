package it.uniroma3.pacman.staticObjects;

import it.uniroma3.pacman.staticObjectGraphics.TeleportView;

public class Teleport {
	private Teleport nextTeleport;
	
	private TeleportView view;

	public Teleport(int x, int y) {
		view = new TeleportView(x, y);
	}

	public Teleport getNextTeleport() {
		return nextTeleport;
	}

	public void setNextTeleport(Teleport nextTeleport) {
		this.nextTeleport = nextTeleport;
	}

	public TeleportView getView() {
		return view;
	}
	
}
