package it.uniroma3.pacman.game;

import it.uniroma3.pacman.movingObjects.MovingObject;
import it.uniroma3.pacman.pacman.PacMan;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
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

			if (e.getCode() == KeyCode.DOWN) {
				//    if ( e.getCode() == KeyCode.VK_DOWN ) {
				pacMan.setKeyboardBuffer(MovingObject.MOVE_DOWN);
			} else if (e.getCode() == KeyCode.UP) {
				pacMan.setKeyboardBuffer(MovingObject.MOVE_UP);
			} else if (e.getCode() == KeyCode.RIGHT) {
				pacMan.setKeyboardBuffer(MovingObject.MOVE_RIGHT);
			} else if (e.getCode() == KeyCode.LEFT) {
				pacMan.setKeyboardBuffer(MovingObject.MOVE_LEFT);
			}
		
	}

}
