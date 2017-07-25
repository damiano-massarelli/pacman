package it.uniroma3.pacman.game;

import it.uniroma3.pacman.characters.PacMan;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardEventHandler implements EventHandler<KeyEvent> {
	
	PacmanGame pacmanGame;
	
	public KeyboardEventHandler(PacmanGame pacmanGame) {
		this.pacmanGame = pacmanGame;
	}

	@Override
	public void handle(KeyEvent e) {
			if (pacmanGame.isWaitingForStart()) {
				pacmanGame.setWaitingForStart(false);
				if (pacmanGame.lastGameResultIsGameOver())
					pacmanGame.startNewGame();
				else
					pacmanGame.startNewLevel();
				return;
			}
			
			PacMan pacMan = pacmanGame.getPacMan();
			pacMan.setKeyboardBuffer(e.getCode());
		
	}

}
