package it.uniroma3.pacman.game;


import java.util.ArrayList;
import java.util.List;

import it.uniroma3.pacman.graphics.Sprite;
import it.uniroma3.pacman.graphics.characters.GhostSprite;
import it.uniroma3.pacman.graphics.characters.PacManSprite;
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
	private PacManSprite pacManSprite;
	private List<GhostSprite> ghostSprites;

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
		
		ghostSprites = new ArrayList<>();
	}
	
	public void setKeyboardHandler(KeyboardEventHandler handler) {
		gameField.setOnKeyPressed(handler);
	}

	public void addSpriteToGameField(Sprite sprite) {
		this.gameField.getChildren().add(sprite);
	}
	
	public Pane getGameField() {
		return this.gameField;
	}
	
	public void setPacManSprite(PacManSprite sprite) {
		this.pacManSprite = sprite;
		addSpriteToGameField(pacManSprite);
	}
	
	public void addGhostSprite(GhostSprite sprite) {
		this.ghostSprites.add(sprite);
		addSpriteToGameField(sprite);
	}

	public void stopAndHideCharacters() {
		pacManSprite.stop();
		pacManSprite.setVisible(false);
		for (GhostSprite g : ghostSprites) {
			g.setVisible(false);
			g.stop();
		}
	}
	
	public void gameOver() {
		messageBox.setText("GAME OVER. PRESS ANY KEY\nTO RESTART");
		messageBox.setVisible(true);
		
	}

	public void levelCompleted() {	
		messageBox.setText("LEVEL COMPLETED! PRESS ANY\nKEY TO START NEXT LEVEL");
		messageBox.setVisible(true);

		pacManSprite.stop();
		pacManSprite.setVisible(false);

		for (GhostSprite g : ghostSprites) {
			g.stop();
			g.setVisible(false);
		}
	}

	public void startNewGame() {
		messageBox.setVisible(false);

		SharedMazeData.resetDots();
		pacManSprite.resetStatus();

		for (GhostSprite g : ghostSprites) {
			g.resetStatus();
		}
	}
	
	public void startNewLevel() {
		messageBox.setVisible(false);
		SharedMazeData.resetDots();
		pacManSprite.resetStatus();

		for (GhostSprite g : ghostSprites) {
			g.resetStatus();
		}

	}

	// reset status and start a new life
	public void startNewLife() {
		pacManSprite.resetStatus();

		for (GhostSprite g : ghostSprites) {
			g.resetStatus();
		}
	}
}
