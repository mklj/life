package com.mklj.life;

import com.mklj.utils.Array2D;
import com.mklj.utils.Math;

import java.util.Observable;
import java.util.Random;

public class LifeModel extends Observable {


	private Array2D.Int grid;
	private int proba;
	private int generationNumber;
	
	/**
	 * <p>Seul constructeur.<br />
	 * La grille est rectangulaire
	 * </p>
	 * @param width - nombre de colonnes
	 * @param height - nombre de lignes
	 * @param proba - % probabilité de générer une cellule vivante
	 */
	public LifeModel(int width, int height, int proba) {
		this.proba = proba;
		setRandomGrid(width, height, proba);
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

	public Array2D.Int getGrid() {
		return grid;
	}

	public void setGrid(Array2D.Int newGrid) {
		this.grid = newGrid;
		updateGrid();
	}
	
	public void cleanGrid() {
		Array2D.Int blankGrid = new Array2D.Int(grid.getWidth(), grid.getHeight());
		blankGrid.fill(0);
		generationNumber = 0;
		this.setGrid(blankGrid);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < grid.getWidth(); i++) {
			s.append("\t");
			for (int j = 0; j < grid.getHeight(); j++)
				if (grid.getValue(i, j) == 0) s.append(". ");
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
		int w = grid.getWidth() - 1;
		int h = grid.getHeight() - 1;
		int a = Math.divisorSignModulo(x - 1, w);
		int b = Math.divisorSignModulo(y-1, h);
		int c = (x+1) % w;
		int d = (y+1) % h;
		return (grid.getValue(a, b) + grid.getValue(x, b) + grid.getValue(c, b) +
				grid.getValue(a, y) +                     + grid.getValue(c, y) +
				grid.getValue(a, d) + grid.getValue(x, d) + grid.getValue(c, d));
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
	 * @param x abscisse de la cellule considérée dans la grille
	 * @param y ordonnée de la cellule considérée dans la grille
	 * @return 1 si la cellule considérée sera vivante à la génération
	 * suivante, 0 sinon.
	 */
	private int nextState(int x, int y) {
		int voisins = countNeighbours(x, y);
		if (grid.getValue(x, y) == 1) {
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
		Array2D.Int nextGrid = new Array2D.Int(grid.getWidth(), grid.getHeight());
		// pour chaque cellule, determiner le prochain état, puis l'écrire
		// dans la nouvelle grille
		for (int i = 0; i < grid.getWidth(); i++) {
			for (int j=0; j < grid.getHeight(); j++) {
				nextGrid.setValue(i, j, this.nextState(i, j));
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
	 * @param height nombre de colonnes
	 * @param width nombre de lignes
	 * @param proba %probability that a given cell is populated
	 * @return the newly generated grid
	 */
	private static Array2D.Int generateRandomGrid(int width, int height, int proba) {
		Array2D.Int randGrid = new Array2D.Int(width, height);
		Random r = new Random();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int genRand = r.nextInt(100) + 1;
				if (genRand <= proba) randGrid.setValue(i, j, 1);
				else randGrid.setValue(i, j, 0);
			}
		}
		return randGrid;
	}
	
	/**
	 * Remplace la grille par une grille nouvellement générée
	 * @param width columns count
	 * @param height lines count
	 * @param proba %probability that a given cell is populated
	 */
	public void setRandomGrid(int width, int height, int proba) {
		generationNumber = 0;
		this.setGrid(generateRandomGrid(width, height, proba));
	}
	
}
