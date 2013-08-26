package com.mklj.life;

import com.mklj.utils.Array2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class GridPanel extends JPanel implements MouseListener {
	
	private static final int PREFERRED_SIZE = 600;
	
	private Array2D.Int grid;
	
	public GridPanel(Array2D.Int grid) {
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
		double cellSize = getPanelMinDimension() / grid.getMaxDimension();
		double xTranslation = (this.getWidth() - cellSize * grid.getWidth()) / 2;
		double yTranslation = (this.getHeight() - cellSize * grid.getHeight()) / 2;
		Graphics2D g2 = (Graphics2D) g;
		// draw border
		g2.setColor(Color.DARK_GRAY);
		Rectangle2D r = new Rectangle2D.Double(
				xTranslation, yTranslation,
				cellSize * grid.getWidth(), cellSize * grid.getHeight());
		g2.draw(r);
		// draw cells
		g2.setColor(Color.BLACK);
		for (int i = 0; i < grid.getWidth(); i++) {
			for (int j = 0; j < grid.getHeight(); j++) {
				if (grid.getValue(i, j) == 1) {
					Ellipse2D e = new Ellipse2D.Double(
							xTranslation + i*cellSize,
							yTranslation + j*cellSize,
							cellSize, cellSize);
					g2.fill(e);
				}
			}	
		}
	}

	public void setGrid(Array2D.Int newGrid) {
		this.grid = newGrid;
	}

	/**
	 * @return the smallest dimension between width and height
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
