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
	private Teleport previousTeleport = null;
	private MazeAssets mazeAssets;
	/**
	 * Creates a new instance of MazeFileLoader
	 * @param filePath the maze file to read
	 * @throws IOException
	 */
	public MazeFileLoader(String filePath) throws IOException {
		mazeAssets = new MazeAssets();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(ResourceManager.getInstance().getResourceAsStream(filePath), "UTF-8"));
			readMazeData(br);
		} catch (IOException e) {
			System.out.println("impossibile caricare il labirinto");
			throw e;
		} finally {
			if (br != null)
				br.close();
		}
	}
	
	/**
	 * Reads the file and puts its content in a {@link MazeAssets} object
	 * @throws IOException
	 * @see {@link MazeFileLoader#getMazeAssets()}
	 */
	private void readMazeData(BufferedReader br) throws IOException {
		String line;
		int yPos = 0;
		int xPos = 0;
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
		
	}

	private void setMazeData(int xPos, int yPos, char blockType) {
		switch (blockType) {
		case 'X':
			mazeAssets.getBlockMatrix().setBlockAt(xPos, yPos);
			break;
		case '.':
							  // convert matrix pos to absolute pos
			Dot dot = new Dot(xPos * MazeConstants.GRID_GAP, yPos * MazeConstants.GRID_GAP); 
			mazeAssets.addDot(dot);
			break;
		case '+':
			Dot magicDot = new MagicDot(xPos * MazeConstants.GRID_GAP, yPos * MazeConstants.GRID_GAP);
			mazeAssets.addDot(magicDot);
			break;
		case 'U':
			mazeAssets.getBlockMatrix().setCageBoundaryBlockAt(xPos, yPos);
			break;
		case 'T':
			Teleport teleport = new Teleport(xPos * MazeConstants.GRID_GAP, yPos * MazeConstants.GRID_GAP);
			if (previousTeleport == null)
				previousTeleport = teleport;
			else {
				teleport.setNextTeleport(previousTeleport);
				previousTeleport.setNextTeleport(teleport);
				previousTeleport = null;
			}
			
			mazeAssets.addTeleport(teleport);
			break;
		}
	}
	
	public MazeAssets getMazeAssets() {
		return mazeAssets;
	}

}