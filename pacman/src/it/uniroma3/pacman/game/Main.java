package it.uniroma3.pacman.game;

import java.io.IOException;

import it.uniroma3.pacman.maze.MazeAssets;
import it.uniroma3.pacman.maze.MazeFileLoader;
import it.uniroma3.pacman.maze.MazeConstants;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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
		MazeFileLoader loader = new MazeFileLoader("/maze.txt");
		MazeAssets mazeAssets = loader.getMazeAssets();
		PacmanGame game = new PacmanGame(mazeAssets);
		primaryStage.setTitle("PacMan - POO");
		//primaryStage.setResizable(true);

		final Group root = new Group();
		final Scene scene = new Scene(root);
		scene.setFill(Color.BLACK);
		
		root.getChildren().add(game.getView());
		
		primaryStage.setWidth(mazeAssets.getBlockMatrix().getWidth() * MazeConstants.GRID_GAP);
		primaryStage.setHeight(mazeAssets.getBlockMatrix().getHeight() * MazeConstants.GRID_GAP + 100);
		primaryStage.setScene(scene);
		primaryStage.show();
		game.getView().requestFocus();
	}

}
