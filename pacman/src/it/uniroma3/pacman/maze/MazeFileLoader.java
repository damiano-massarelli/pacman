package it.uniroma3.pacman.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import it.uniroma3.pacman.staticObjects.Teleport;
import it.uniroma3.pacman.staticObjects.Dot;
import it.uniroma3.pacman.staticObjects.MagicDot;
import it.uniroma3.resources.ResourceManager;

/**
 * Reads a simple maze file and puts its data in SharedMazeData.
 * A maze file describes a maze using predefined chars for blocks, dots, 
 * teleport ecc... (see maze.txt). Every maze file must contain the maze dimension
 * in the first line: &lt;width&gt;, &lt;height&gt;
 * @author damiano
 *
 */
public class MazeFileLoader {
	Teleport firstTeleport = null;
	BufferedReader br;

	/**
	 * Creates a new instance of MazeFileLoader
	 * @param filePath the maze file to read
	 * @throws IOException
	 */
	public MazeFileLoader(String filePath) throws IOException {

		this.br = null;
		try {
			br = new BufferedReader(new InputStreamReader(ResourceManager.getInstance().getResourceAsStream("/maze.txt"), "UTF-8"));
		} catch (IOException e) {
			br.close();
			throw e;
		}
	}
	
	/**
	 * Reads the file and puts its content in SharedMazeData
	 * @throws IOException
	 */
	public void readMazeData() throws IOException {
		String line;
		int yPos = 0;
		int xPos = 0;
		try {
			line = br.readLine();
			String[] dimension = line.split(",");
			int mazeWidth = Integer.parseInt(dimension[0]);
			int mazeHeight = Integer.parseInt(dimension[1]);
			SharedMazeData.create(mazeWidth, mazeHeight);
		} catch (Exception e) {
			throw new IOException("First line of maze file must contain maze's dimension as integers: <width>,<height>");
		}
		
		while ((line = br.readLine()) != null) {
			if (line.startsWith("#")) continue; // la linea è un commento
			xPos = 0;
			for (int x = 0; x < line.length(); x++) {
				char currentChar = line.charAt(x);
				if (currentChar != ' ') { // spazi usati per leggibilita' del labirinto
					setMazeData(xPos, yPos, line.charAt(x));
					xPos++;
				}
			}
			yPos++;
		}
		br.close();
	}

	private void setMazeData(int xPos, int yPos, char blockType) {
		int value = SharedMazeData.BLOCK;
		Dot dot = null;
		switch (blockType) {
		case 'X':
			value = SharedMazeData.BLOCK;
			break;
		case '-':
			value = SharedMazeData.EMPTY;
			break;
		case '.':
			value = SharedMazeData.NORMAL_DOT;
			dot = new Dot(SharedMazeData.xPositionForGridX(xPos), SharedMazeData.yPositionForGridY(yPos));
			break;
		case '+':
			value = SharedMazeData.MAGIC_DOT;
			dot = new MagicDot(SharedMazeData.xPositionForGridX(xPos), SharedMazeData.yPositionForGridY(yPos));
			break;
		case 'U':
			value = SharedMazeData.CAGE_BOUNDARY_LIMIT;
			break;
		case 'T':
			value = SharedMazeData.TELEPORT;
			Teleport teleport = new Teleport(SharedMazeData.xPositionForGridX(xPos), SharedMazeData.yPositionForGridY(yPos));
			if (firstTeleport != null) {
				firstTeleport.setNextTeleport(teleport);
				teleport.setNextTeleport(firstTeleport);
			}
			else
				firstTeleport = teleport;
			
			SharedMazeData.setTeleport(teleport);
			break;
		}

		if (dot != null) 
			SharedMazeData.setDot(xPos, yPos, dot);
			

		SharedMazeData.setData(xPos, yPos, value);
	}

}