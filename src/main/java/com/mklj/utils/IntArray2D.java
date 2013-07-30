package com.mklj.utils;

/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 29/07/13
 * Time: 23:56
 * To change this template use File | Settings | File Templates.
 */
public class IntArray2D {

	private int width;
	private int height;
	private int[][] tab;

	public IntArray2D(int width, int height) {
		this.width = width;
		this.height = height;
		this.tab = new int[width][height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMaxDimension() {
		return (this.width >= this.height) ? this.width: this.height;
	}

	public void fill(int num) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tab[i][j] = num;
			}
		}
	}

}
