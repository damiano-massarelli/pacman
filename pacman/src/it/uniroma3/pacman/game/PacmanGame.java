package it.uniroma3.pacman.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import it.uniroma3.pacman.collision.CollisionDetector;
import it.uniroma3.pacman.collision.DotCollisionTrigger;
import it.uniroma3.pacman.collision.TeleportCollisionTrigger;
import it.uniroma3.pacman.collision.handlers.AutomaticCollisionHandler;
import it.uniroma3.pacman.collision.handlers.MovingObjectTeleportCollisionHandler;
import it.uniroma3.pacman.collision.handlers.PacManDotCollisionHandler;
import it.uniroma3.pacman.collision.handlers.PacManGhostCollisionHandler;
import it.uniroma3.pacman.collision.handlers.PacManMagicDotCollisionHandler;
import it.uniroma3.pacman.ghosts.Ghost;
import it.uniroma3.pacman.maze.MazeFileLoader;
import it.uniroma3.pacman.maze.MazeBackgroundGraphics;
import it.uniroma3.pacman.maze.SharedMazeData;
import it.uniroma3.pacman.pacman.PacMan;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.pacman.staticObjects.Teleport;
import it.uniroma3.pacman.ui.MessageBox;
import it.uniroma3.pacman.ui.CustomText;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Maze.fx created on 2<<008-12-20, 20:22:15 <br>
 * Maze.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */

public class PacmanGame extends VBox {

	// Pac-Man Character
	public PacMan pacMan;

	public final List<Ghost> ghosts;

	// level of the game
	private SimpleIntegerProperty level;
	
	private boolean lastGameResultIsGameOver = true;

	private boolean waitingForStart;

	private MessageBox messageBox;

	private final Pane gameField;
	
	private CollisionDetector collisionDetector;

	public PacmanGame() throws IOException {
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		MazeBackgroundGraphics backgroundGraphics = new MazeBackgroundGraphics();
		MazeFileLoader builder = new MazeFileLoader("/resources/maze.txt");
		builder.readMazeData();
		backgroundGraphics.createBackground();
		
		setFocused(true);
		
		gameField = new Pane(); 
		gameField.setPrefSize(SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP, SharedMazeData.getGridHeight() * SharedMazeData.GRID_GAP);

		getChildren().add(gameField);

		ghosts = new ArrayList<Ghost>(4);
		new CharactersSetup().setup(this);
		level = new SimpleIntegerProperty(1);
		
		
		waitingForStart = true;

		messageBox = new MessageBox("PRESS ANY KEY TO START");

		gameField.getChildren().add(backgroundGraphics);

		CustomText textScore = new CustomText(pacMan.getScoreProperty().asString("SCORE: %1d"));
		getChildren().add(textScore);


		Text textLevel = new CustomText(level.asString("LEVEL: %1d "));
		getChildren().add(textLevel);
		
		Text textLives = new CustomText(pacMan.getLivesProperty().asString("LIVES: %1d"));
		getChildren().add(textLives);
		
		gameField.setFocusTraversable(true); // patweb
		gameField.setOnKeyPressed(new KeyboardEventHandler(this));

		gameField.getChildren().add(pacMan);
		gameField.getChildren().addAll(ghosts);
		
		// insert messageBox
		gameField.getChildren().add(messageBox);

		
		// Collision handlers
		AutomaticCollisionHandler auto = new AutomaticCollisionHandler();
		auto.addCollisionHandler(new PacManDotCollisionHandler(this), PacMan.class, Dot.class);
		PacManGhostCollisionHandler pacManGhostCollisionHandler = new PacManGhostCollisionHandler(ghosts, this);
		auto.addCollisionHandler(pacManGhostCollisionHandler, PacMan.class, Ghost.class);
		auto.addCollisionHandler(new PacManMagicDotCollisionHandler(ghosts, pacManGhostCollisionHandler), PacMan.class, MagicDot.class);
		
		
		MovingObjectTeleportCollisionHandler teleportHandler = new MovingObjectTeleportCollisionHandler();
		auto.addCollisionHandler(teleportHandler, PacMan.class, Teleport.class);
		auto.addCollisionHandler(teleportHandler, Ghost.class, Teleport.class);
		
		collisionDetector = new CollisionDetector();
		collisionDetector.addCollidable(pacMan);
		for (Ghost g : ghosts)
			collisionDetector.addCollidable(g);
		collisionDetector.addTrigger(new DotCollisionTrigger(pacMan));
		collisionDetector.addTrigger(new TeleportCollisionTrigger(pacMan, ghosts));
		collisionDetector.addCollisionHandler(auto);
	}

	public Pane getGameField() {
		return this.gameField;
	}
	
	public void addGhost(Ghost ghost) {
		ghosts.add(ghost);
	}
	
	
	public PacMan getPacMan() {
		return pacMan;
	}

	public void setPacMan(PacMan pacMan) {
		this.pacMan = pacMan;
	}

	public boolean lastGameResultIsGameOver() {
		return lastGameResultIsGameOver;
	}

	public void setLastGameResultIsGameOver(boolean lastGameResultIsGameOver) {
		this.lastGameResultIsGameOver = lastGameResultIsGameOver;
	}

	public boolean isWaitingForStart() {
		return waitingForStart;
	}

	public void setWaitingForStart(boolean waitingForStart) {
		this.waitingForStart = waitingForStart;
	}

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	public void gameOver() {
		lastGameResultIsGameOver = true;
		waitingForStart = true;
		messageBox.setText("GAME OVER. PRESS ANY KEY\nTO RESTART");
		messageBox.setVisible(true);
		pacMan.stop();
		for (Ghost g : ghosts)
			g.stop();
	}
	
	public void levelCompleted() {
		lastGameResultIsGameOver = false;
		waitingForStart = true;
		
		messageBox.setText("LEVEL COMPLETED! PRESS ANY\nKEY TO START NEXT LEVEL");
		messageBox.setVisible(true);
		
		pacMan.hide();
		
		for (Ghost g : ghosts) {
			g.hide();
		}
			
	}

	// reset status and start a new game
	public void startNewGame() {
		messageBox.setVisible(false);
		level.set(1);
		
		
		SharedMazeData.resetDots();
		pacMan.setScore(0);
		pacMan.setDotEatenCount(0);
		pacMan.resetStatus();

		pacMan.getLivesProperty().set(3);

		for (Ghost g : ghosts) {
			g.resetStatus();
		}

	}

	// reset status and start a new level
	public void startNewLevel() {
		messageBox.setVisible(false);
		SharedMazeData.resetDots();
		pacMan.resetStatus();
		pacMan.setDotEatenCount(0);
		level.set(level.get() + 1);;

		for (Ghost g : ghosts) {
			g.resetStatus();
		}

	}

	// reset status and start a new life
	public void startNewLife() {
		pacMan.resetStatus();

		for (Ghost g : ghosts) {
			g.resetStatus();
		}
	}

}
