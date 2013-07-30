package com.mklj.utils;

/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 30/07/13
 * Time: 07:09
 * To change this template use File | Settings | File Templates.
 */
public abstract class Array2D {

	private int width;
	private int height;

	public Array2D(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMaxDimension() {
		return (this.width >= this.height) ? this.width : this.height;
	}

	@Override
	public abstract String toString();


	public static class Int extends Array2D {

		private int[][] table;

		public Int(int width, int height) {
			super(width, height);
			table = new int[width][height];
		}


		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < getWidth(); i++) {
				for (int j = 0; j < getHeight(); j++) {
					sb.append(table[i][j]).append(" ");
				}
				sb.append("\n");
			}
			return sb.toString();
		}

		public int getValue(int x, int y) {
			return table[x][y];
		}

		public void setValue(int x, int y, int newValue) {
			table[x][y] = newValue;
		}

		public void fill(int num) {
			for (int i = 0; i < getWidth(); i++) {
				for (int j = 0; j < getHeight(); j++) {
					table[i][j] = num;
				}
			}
		}

	}

}
