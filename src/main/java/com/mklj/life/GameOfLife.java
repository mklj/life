package com.mklj.life;

public class GameOfLife {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// creer modele
		LifeModel m = new LifeModel(100, 100, 50);

		// creer controleur
		LifeController c = new LifeController(m);
		
		// demander au controleur d'afficher les vues
		c.creerVue();

	}

}
