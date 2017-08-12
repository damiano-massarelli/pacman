package it.uniroma3.pacman.staticObjects;

import it.uniroma3.pacman.graphics.CollidableModelEntity;
import it.uniroma3.pacman.staticObjectGraphics.TeleportView;

public class Teleport implements CollidableModelEntity {
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

	@Override
	public TeleportView getView() {
		return view;
	}
	
}
