package it.uniroma3.pacman.game;

import java.io.IOException;

import it.uniroma3.pacman.maze.SharedMazeData;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main.fx created on 2008-12-20, 12:02:26 <br>
 * Main.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class Main extends Application {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		PacmanGame maze = new PacmanGame();
		primaryStage.setTitle("Pac-Man by Henry Zhang www.javafxgame.com and Patrick Webster");
		//primaryStage.setResizable(true);

		final Group root = new Group();
		final Scene scene = new Scene(root);
		root.getChildren().add(maze);
		primaryStage.setWidth(SharedMazeData.getGridWidth() * SharedMazeData.GRID_GAP);
		primaryStage.setHeight(SharedMazeData.getGridHeight() * SharedMazeData.GRID_GAP + 100);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
