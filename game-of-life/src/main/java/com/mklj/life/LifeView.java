package com.mklj.life;

import com.mklj.utils.Array2D;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class LifeView extends JFrame implements Observer {
	
	private static final int WINDOW_DEFAULT_WIDTH = 800;
	private static final int WINDOW_DEFAULT_HEIGHT = 800;
	private static final String WINDOW_TITLE = "Game of life - generation #";
	private static final String RANDOM_BUTTON = "random grid";
	private static final String CLEAN_BUTTON = "clean grid";
	private static final String NEXT_GEN_BUTTON = "next gen";
	private static final String START_BUTTON = "play / pause";
	private static final String SLIDE_LABEL_TEXT = " speed";

	private LifeController controller;
	private GridPanel gp;
	private Button random;
	private Button clean;
	private Button nextGen;
	private Button animate;
	private Box vbox;
	private JSlider slide;
	private JLabel slideLabel;
	private Box slideBox;
	boolean animated;
	
	public LifeView(LifeController controller) {
		super();
		this.controller = controller;
		animated = false;
		this.setTitle(WINDOW_TITLE + controller.getGenerationNumber());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(WINDOW_DEFAULT_WIDTH, WINDOW_DEFAULT_HEIGHT);
		this.setResizable(true);
		
		// layout
		this.setLayout(new BorderLayout(10, 10));
		
		// GridPanel with BoxLayout
		gp = new GridPanel(controller.getGrid());
		this.add(gp, BorderLayout.CENTER);
		
		
		// button : random grid
		random = new Button(RANDOM_BUTTON);
		random.addActionListener(new RandomListener());
		
		// button : reset grid
		clean = new Button(CLEAN_BUTTON);
		clean.addActionListener(new CleanListener());
		
		// button : next generation
		nextGen = new Button(NEXT_GEN_BUTTON);
		nextGen.addActionListener(new NextGenListener());

		// button : start animation
		animate = new Button(START_BUTTON);
		animate.addActionListener(new AnimationListener());
		
		// slider
		slide = new JSlider(0, 9);
		slide.setValue((500 - controller.getSleepTime()) / 50 );
		slide.setPaintTicks(true);
		slide.setMinorTickSpacing(1);
		slide.setOrientation(JSlider.VERTICAL);
//		slide.setAlignmentX(CENTER_ALIGNMENT);
		slide.addChangeListener(new SlideListener());
		
		// label pour le slider
		slideLabel = new JLabel(SLIDE_LABEL_TEXT);
		slideLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		// vbox : slide + label
		slideBox = new Box(BoxLayout.Y_AXIS);
		slideBox.add(slide);
		slideBox.add(slideLabel);
		
		// vbox pour les boutons et le slider
		vbox = new Box(BoxLayout.Y_AXIS);
		vbox.add(random);
		vbox.add(clean);
		vbox.add(nextGen);
		vbox.add(animate);
		vbox.add(slideBox);
		
		// add widgets to layout
		this.add(vbox, BorderLayout.LINE_END);
	}
	
	private void updateWindowTitle() {
		this.setTitle(WINDOW_TITLE + controller.getGenerationNumber());
	}

	@Override
	public void update(Observable o, Object changedData) {
		Object[] data = (Object[]) changedData;
		gp.setGrid((Array2D.Int) data[0]);
		updateWindowTitle();
//		gp.repaint(); // ne fonctionne pas ...?
		this.repaint();
	}
	
	/*************
	 * LISTENERS *
	 */
	public class RandomListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.setNewRandomGrid();
		}
	}
	
	public class CleanListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.cleanGrid();
			updateWindowTitle();
		}
	}
	
	public class NextGenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.nextGen();
			updateWindowTitle();
		}
	}
	
	public class AnimationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (animated) {
				animated = false;
				clean.setEnabled(true);
				nextGen.setEnabled(true);
				animate.setEnabled(true);
				controller.stopAnimation();
			}
			else {
				animated = true;
				clean.setEnabled(false);
				nextGen.setEnabled(false);
				controller.startAnimation();
			}
		}
	}
	
	public class SlideListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			int sliderValue = ((JSlider) e.getSource()).getValue();
			controller.setSleepTime(500 - sliderValue * 50);
			
		}
		
	}
}
