package com.mklj.life;

import com.mklj.utils.RectArray;

import java.util.Observable;
import java.util.Random;

public class LifeModel extends Observable {
	

	private RectArray grid;
	private int proba;
	private int generationNumber;
	
	/**
	 * <p>Seul constructeur.<br />
	 * La grille est rectangulaire
	 * </p>
	 * @param grid
	 * @param proba
	 */
	public LifeModel(RectArray grid, int proba) {
		this.grid = grid;
		this.proba = proba;
		this.setGrid(generateRandomGrid(grid, proba));
		generationNumber = 0;
	}

	public int getGenerationNumber() {
		return generationNumber;
	}

	/**
	 * @return the proba
	 */
	public int getProba() {
		return proba;
	}

	public RectArray getGrid() {
		return grid;
	}

	public void setGrid(RectArray newGrid) {
		this.grid = newGrid;
		updateGrid();
	}
	
	public void cleanGrid() {
		RectArray blankGrid = new RectArray(grid.getWidth(), grid.getHeight());
		blankGrid.fill(0);
		generationNumber = 0;
		this.setGrid(blankGrid);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		
		for (int i = 0; i < height; i++) {
			s.append("\t");
			for (int j = 0; j < width; j++)
				if (grid[i][j] == 0) s.append(". ");
				else s.append("o ");
			s.append("\n");
		}
		return s.toString();
	}
	
	/**
	 * <p>
	 * Dénombre le nombre de voisins vivants d'une cellule de coordonnées x,y.
	 * Toute case de la grille a exactement 8 voisins.<br />
	 * n = indice maximum permis par le tableau.<br />
	 * En cas de débordement supérieur de la grille (ie x+1 ou y+1 > n),
	 * l'indice et ramené à zéro.<br />
	 * En cas de débordement inférieur (x-1 ou y-1 < 0), l'indice est augmenté
	 * à n.</p>
	 * <div>
	 * <table border="1">
	 * <tr>
	 * 	<td></td><td></td><td>(x)</td><td></td>
	 * </tr>
	 * <tr>
	 * 	<td></td><td>x-1,y-1</td><td>x,y-1</td><td>x+1,y-1</td>
	 * </tr>
	 * <tr>
	 * 	<td>(y)</td><td>x-1,y</td><td></td><td>x+1,y</td>
	 * </tr>
	 * <tr>
	 * 	<td></td><td>x-1,y+1</td><td>x,y+1</td><td>x+1,y+1</td>
	 * </tr>
	 * </table>
	 * </div>
	 * @param x - abscisse de la cellule considérée dans la grille
	 * @param y - ordonnée de la cellule considérée dans la grille
	 * @return Nombre de voisins vivants de la cellule considérée.
	 */
	private int countNeighbours(int x, int y) {
		int n = grid.length - 1;
		int a = (x-1 < 0) ? n : x-1;
		int b = (y-1 < 0) ? n : y-1;
		int c = (x+1 > n) ? 0 : x+1;
		int d = (y+1 > n) ? 0 : y+1;
		return (grid[a][b] + grid[x][b] + grid[c][b] +
				grid[a][y] +              grid[c][y] +
				grid[a][d] + grid[x][d] + grid[c][d]);
	}
	
	/**
	 * <p>
	 * Détermine l'état de la cellule considérée à la prochaine génération.
	 * <ul>
	 * 	<li>Si une cellule a 2 ou 3 voisins à la génération t, alors elle
	 * survit à la génération t+1, sinon elle meurt.</li>
	 * 	<li>Si une case vide possède exactement 3 cellules voisines vivantes à
	 * la génération t, alors une cellule naît dans cette case à la génération
	 * t+1</li>
	 * </ul>
	 * @param x - abscisse de la cellule considérée dans la grille
	 * @param y - ordonnée de la cellule considérée dans la grille
	 * @return
	 */
	private int nextState(int x, int y) {
		int voisins = countNeighbours(x, y);
		if (grid[x][y] == 1) {
			if (voisins == 2 || voisins == 3) return 1;
			else return 0;
		}
		else {
			if (voisins == 3) return 1;
			else return 0;
		}
	}
	
	/**
	 * Modifie la grille selon les règles de génération.
	 */
	public void generateNextGrid() {
		// créer nouvelle grille
		int[][] nextGrid = new int[height][width];
		// pour chaque cellule, determiner le prochain état, puis l'écrire
		// dans la nouvelle grille
		for (int i = 0; i < height; i++) {
			for (int j=0; j < width; j++) {
				nextGrid[i][j] = this.nextState(i, j);
			}
		}
		generationNumber++;
		this.setGrid(nextGrid);
	}
	
	/**
	 * Déclenche la notification d'un changement de la grille à tous les
	 * observateurs, et leur envoie la nouvelle grille.
	 */
	public void updateGrid() {
		Object[] changedData = {grid, generationNumber};
		setChanged();
		notifyObservers(changedData);
	}
	
	/**
	 * Generates a new grid at random.
	 * @param height
	 * @param width
	 * @param proba - %probability that a given cell is populated
	 * @return the newly generated grid
	 */
	private static int[][] generateRandomGrid(int height, int width, int proba) {
		int[][] newGrid = new int[height][width];
		Random r = new Random();
		int genRand = 0;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				genRand = r.nextInt(100) + 1;
				if (genRand <= proba) newGrid[i][j] = 1;
				else newGrid[i][j] = 0;
			}
		}
		return newGrid;
	}
	
	/**
	 * Remplace la grille par une grille nouvellement générée
	 * @param height
	 * @param width
	 * @param proba - %probability that a given cell is populated
	 */
	public void setRandomGrid(int height, int width, int proba) {
		generationNumber = 0;
		this.setGrid(generateRandomGrid(height, width, proba));
	}
	
}
