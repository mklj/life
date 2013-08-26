package com.mklj.life;

import com.mklj.utils.Array2D;

public class LifeController {

	private static final int DEFAULT_SLEEP_TIME = 100;
	
	private LifeModel model;
	private Thread t;
	private volatile int sleepTime; // useless keyword volatile
	
	public LifeController(final LifeModel model) {
		this.model = model;
		t = null;
		sleepTime = DEFAULT_SLEEP_TIME;
	}

	/**
	 * @return the sleepTime
	 */
	public int getSleepTime() {
		return sleepTime;
	}

	/**
	 * @param sleepTime the sleepTime to set, in milliseconds
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void createLifeView() {
		LifeView vue = new LifeView(this);
		model.addObserver(vue);
		vue.setVisible(true);
	}
	
	public void startAnimation() { 
		
		t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (t != null) {
					model.generateNextGrid();
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}			
			}
		});
		t.start();
	}
	
	public void stopAnimation() {
		t = null;
	}
	
	public Array2D.Int getGrid() {
		return model.getGrid();
	}
	
	public void cleanGrid() {
		model.cleanGrid();
	}

	public void nextGen() {
		model.generateNextGrid();
	}
	
	public int getGenerationNumber() {
		return model.getGenerationNumber();
	}
	
	public void setNewRandomGrid() {
		model.setRandomGrid(
				model.getGrid().getWidth(), model.getGrid().getHeight(),
				model.getProba());
	}


}

