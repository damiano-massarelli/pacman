package it.uniroma3.pacman.game;


import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.graphics.View;
import it.uniroma3.pacman.graphics.characters.GhostView;
import it.uniroma3.pacman.graphics.characters.PacManView;
import it.uniroma3.pacman.maze.MazeBackgroundGraphics;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.ui.CustomText;
import it.uniroma3.pacman.ui.MessageBox;
import javafx.beans.property.IntegerProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PacmanGameView extends VBox {

	private Pane gameField;
	private MessageBox messageBox;
	private PacManView pacManView;
	private List<GhostView> ghostViews;

	public PacmanGameView(IntegerProperty level, IntegerProperty pacManScore, IntegerProperty pacManLives) {
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		MazeBackgroundGraphics backgroundGraphics = new MazeBackgroundGraphics();
		backgroundGraphics.createBackground();

		gameField = new Pane(); 
		
		gameField.setPrefSize(SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP, SharedMazeData.getGridHeight() * SharedMazeData.GRID_GAP);
		getChildren().add(gameField);
		gameField.getChildren().add(backgroundGraphics);

		messageBox = new MessageBox("PRESS ANY KEY TO START");
		gameField.getChildren().add(messageBox);

		CustomText textScore = new CustomText(pacManScore.asString("SCORE: %1d"));
		getChildren().add(textScore);

		Text textLevel = new CustomText(level.asString("LEVEL: %1d "));
		getChildren().add(textLevel);

		Text textLives = new CustomText(pacManLives.asString("LIVES: %1d"));
		getChildren().add(textLives);
		
		ghostViews = new ArrayList<>();
	}
	
	public void setKeyboardHandler(KeyboardEventHandler handler) {
		gameField.setOnKeyPressed(handler);
	}

	public void addViewToGameField(View view) {
		this.gameField.getChildren().add(view);
	}
	
	public Pane getGameField() {
		return this.gameField;
	}
	
	public void setPacManView(PacManView view) {
		this.pacManView = view;
		addViewToGameField(pacManView);
	}
	
	public void addGhostView(GhostView view) {
		this.ghostViews.add(view);
		addViewToGameField(view);
	}

	public void gameOver() {
		messageBox.setText("GAME OVER. PRESS ANY KEY\nTO RESTART");
		messageBox.setVisible(true);
		pacManView.stop();
		for (GhostView g : ghostViews)
			g.stop();
	}

	public void levelCompleted() {	
		messageBox.setText("LEVEL COMPLETED! PRESS ANY\nKEY TO START NEXT LEVEL");
		messageBox.setVisible(true);

		pacManView.hide();

		for (GhostView g : ghostViews) {
			g.hide();
		}
	}

	public void startNewGame() {
		messageBox.setVisible(false);

		SharedMazeData.resetDots();
		pacManView.resetStatus();

		for (GhostView g : ghostViews) {
			g.resetStatus();
		}
	}
	
	public void startNewLevel() {
		messageBox.setVisible(false);
		SharedMazeData.resetDots();
		pacManView.resetStatus();

		for (GhostView g : ghostViews) {
			g.resetStatus();
		}

	}

	// reset status and start a new life
	public void startNewLife() {
		pacManView.resetStatus();

		for (GhostView g : ghostViews) {
			g.resetStatus();
		}
	}
}
