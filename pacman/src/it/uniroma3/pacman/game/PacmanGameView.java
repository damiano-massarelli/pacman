package it.uniroma3.pacman.game;


import it.uniroma3.pacman.characters.Ghost;
import it.uniroma3.pacman.graphics.View;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.ui.CustomText;
import it.uniroma3.pacman.ui.MessageBox;
import javafx.beans.property.IntegerProperty;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PacmanGameView extends VBox {

	private Pane gameField;
	private MessageBox messageBox;

	public PacmanGameView(IntegerProperty level, IntegerProperty pacManScore, IntegerProperty pacManLives) {
		setFocused(true);

		gameField = new Pane(); 
		gameField.setFocusTraversable(true); // patweb
		gameField.setPrefSize(SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP, SharedMazeData.getGridHeight() * SharedMazeData.GRID_GAP);
		getChildren().add(gameField);

		messageBox = new MessageBox("PRESS ANY KEY TO START");
		gameField.getChildren().add(messageBox);

		CustomText textScore = new CustomText(pacManScore.asString("SCORE: %1d"));
		getChildren().add(textScore);

		Text textLevel = new CustomText(level.asString("LEVEL: %1d "));
		getChildren().add(textLevel);

		Text textLives = new CustomText(pacManLives.asString("LIVES: %1d"));
		getChildren().add(textLives);
	}

	public void addViewToGameField(View view) {
		this.gameField.getChildren().add(view);
	}

	public void gameOver() {
		messageBox.setText("GAME OVER. PRESS ANY KEY\nTO RESTART");
		messageBox.setVisible(true);
		pacMan.getView().stop();
		for (Ghost g : ghosts)
			g.getView().stop();
	}

	public void levelCompleted() {	
		messageBox.setText("LEVEL COMPLETED! PRESS ANY\nKEY TO START NEXT LEVEL");
		messageBox.setVisible(true);

		pacMan.getView().hide();

		for (Ghost g : ghosts) {
			g.getView().hide();
		}
	}

	public void startNewGame() {
		messageBox.setVisible(false);

		SharedMazeData.resetDots();
		pacMan.getView().resetStatus();

		for (Ghost g : ghosts) {
			g.getView().resetStatus();
		}
	}
	
	public void startNewLevel() {
		messageBox.setVisible(false);
		SharedMazeData.resetDots();
		pacMan.getView().resetStatus();

		for (Ghost g : ghosts) {
			g.getView().resetStatus();
		}

	}

	// reset status and start a new life
	public void startNewLife() {
		pacMan.getView().resetStatus();

		for (Ghost g : ghosts) {
			g.getView().resetStatus();
		}
	}
}
