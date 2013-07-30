package com.mklj.life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class GridPanel extends JPanel implements MouseListener {
	
	private static final int PREFERRED_SIZE = 600;
	
	private int[][] grid;
	
	public GridPanel(int[][] grid) {
		super();
		this.grid = grid;
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREFERRED_SIZE, PREFERRED_SIZE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		double cellSize = getPanelMinDimension() / grid.length;
		double xTranslation = (getWidth() - cellSize * grid.getW) / 2;
		double yTranslation = (getHeight() - cellSize * grid.length) / 2;
		Graphics2D g2 = (Graphics2D) g;
		// draw border
		g2.setColor(Color.BLACK);
		Rectangle2D r = new Rectangle2D.Double(
				xTranslation, yTranslation,
		);
		g2.draw(r);
		// draw cells
		g2.setColor(Color.DARK_GRAY);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == 1) {
					Ellipse2D e = new Ellipse2D.Double(
							xTranslation + i*cellSize,
							yTranslation + j*cellSize,
							cellSize, cellSize);
					g2.fill(e);
				}
//				else {
//					Rectangle2D r = new Rectangle2D.Double(
//							xTranslation + i*cellSize,
//							yTranslation + j*cellSize, cellSize, cellSize);
//					g2.draw(r);
//				}
			}	
		}
	}
	
	public int[][] getGrid() {
		return grid;
	}

	public void setGrid(int[][] newGrid) {
		this.grid = newGrid;
	}

	/**
	 * @return the smallest dimension between height and width
	 */
	public int getPanelMinDimension() {
		return (this.getWidth() < this.getHeight()) ?
			 this.getWidth() : this.getHeight();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
