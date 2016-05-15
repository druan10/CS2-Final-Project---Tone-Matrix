/**
 * Tone Matrix By David Ruan.
 */

import java.io.File;
import java.awt.event.KeyEvent;

public class ToneMatrix {
	/**
	 * Declares variables the ToneMatrix will use
	 */
	private ToneMatrixModel model;
	private int mousex;
	private int mousey;
	private boolean click;
	private int instrument;
	public int bpm;
	private Stopwatch timer;
	private int time;
	private int col;
	private TonePlayer[] saws;
	private TonePlayer[] pianos;
	private TonePlayer[] plucks;

	public static void main(String[] args) {
		/**
		 * Sets the screen size and runs the program
		 */
		StdDraw.setCanvasSize(640, 640);
		new ToneMatrix().run();
	}

	public void run() {
		/**
		 * Sets the default values for variables the program needs to function.
		 * Creates a new ToneMatrixModel that we will be using for our matrix.
		 * Initializes the timer that will be used to calculate BPM Creates a
		 * TonePlayer array for each instrument and fills it with a TonePlayer
		 * for each note.
		 */

		instrument = 1;
		click = false;
		bpm = 120;
		time = 15000 / bpm;
		col = 0;
		model = new ToneMatrixModel();
		timer = new Stopwatch();
		saws = new TonePlayer[16];
		/**
		 * Creates TonePlayer for each sound
		 */
		for (int i = 0; i < 16; i++) {
			saws[i] = new TonePlayer(new File("source" + java.io.File.separator
					+ "saw - Marker #" + i + ".wav"));
		}
		pianos = new TonePlayer[16];
		for (int i = 0; i < 16; i++) {
			pianos[i] = new TonePlayer(new File("source"
					+ java.io.File.separator + "piano - Marker #" + i + ".wav"));
		}
		plucks = new TonePlayer[16];
		for (int i = 0; i < 16; i++) {
			plucks[i] = new TonePlayer(new File("source"
					+ java.io.File.separator + "pluck - Marker #" + i + ".wav"));
		}
		/**
		 * Draws the screen and then runs the main program in a while loop.
		 */
		draw();
		drawInfo();
		while (true) {
			checkMouse();
			checkKey();
			if ((timer.elapsedTime() > (time))) {
				time += (15000 / bpm);
				cycleArray();
			}
		}
	}

	/**
	 * This method checks to see if certain keys are pressed. This is needed to
	 * change instrument as well as bring up the load/save window.
	 */
	public void checkKey() {
		if (StdDraw.isKeyPressed(KeyEvent.VK_1)) {
			instrument = 1;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
			instrument = 2;
		}
		if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
			instrument = 3;
		}

	}

	/**
	 * this method makes sure clicks work correctly by calling mouseClick() and
	 * mouseRelease()
	 */
	public void checkMouse() {
		if (!click) {
			mouseClick();
		}
		if (click) {
			mouseRelease();
			StdDraw.show(0);
		}
	}

	public void mouseClick() {
		if (StdDraw.mousePressed()) {
			click = true;
		}
	}

	public void mouseRelease() {
		if (!StdDraw.mousePressed()) {
			click = false;
			clickSquare();
		}
	}

	public void clickSquare() {
		/**
		 * Detects which square the user has clicked and then toggles the value
		 * of the array.
		 */
		double mx = StdDraw.mouseX();
		double my = StdDraw.mouseY();
		if (mx > .1 && mx < .9 && my > .1 && my < .9) {
			mousex = 0;
			mousey = 0;
			double c = .1;
			while (mx > c) {
				if (mx > c) {
					mousex += 1;
					c += .05;
				} else {
					break;
				}
			}
			c = .1;
			while (my > c) {
				if (my > c) {
					mousey += 1;
					c += .05;
				} else {
					break;
				}
			}

			mousex = mousex - 1;
			mousey = mousey - 1;
			/**
			 * Draws appropriate color square in correct spot
			 */
			if (mousex >= 0 && mousex < 16 && mousey >= 0 && mousey < 16) {
				toggle(mousex, mousey);
				int color = model.getColor(mousex, mousey);
				if (color == 0) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledSquare(.125 + (mousex * .05),
					.125 + (mousey * .05), .024);
					StdDraw.setPenColor(StdDraw.GRAY);
					StdDraw.square(.125 + (mousex * .05),
					.125 + (mousey * .05), .024);
				}
				if (color == 1) {
					StdDraw.picture(.125 + (mousex*.05), .125 + (mousey * .05), "source" + java.io.File.separator + "Grey.jpg");
				}
				if (color == 2) {
					StdDraw.picture(.125 + (mousex*.05), .125 + (mousey * .05), "source" + java.io.File.separator + "Yellow.jpg");
				}
				if (color == 3) {
					StdDraw.picture(.125 + (mousex*.05), .125 + (mousey * .05), "source" + java.io.File.separator + "Blue.jpg");
				}

			}
		}
		if (mx > .875 && mx < .925 && my > 0 && my < .05){
			if (bpm<160){
			bpm++;
			drawInfo();
			}
		}
		if (mx > .075 && mx < .125 && my > 0 && my < .05){
			if (bpm>80){
			bpm--;
			drawInfo();
			}
		}
	}
	/**
	 * Toggles the value of the ToneMatrixModel at index [x][y]
	 */
	public void toggle(int x, int y) {
		if (model.getOn(x, y)) {
			model.setColor(x, y, 0);
		} else {
			model.setColor(x, y, instrument);
		}
	}
	/**
	 * Draws the UI and instructions
	 */
	public void draw() {
		StdDraw.clear();
		StdDraw.picture(.5, .5, "source" + java.io.File.separator + "ToneMatrixBG.jpg");
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(.5, 1, "Tone Matrix by David Ruan");
		StdDraw.text(.5, .95, "Click squares to toggle notes");
		StdDraw.text(.5, .05, "Press 1 for saw, 2 for piano, 3 for pluck");
		StdDraw.picture(.9, .025, "source" + java.io.File.separator + "Right.jpg");
		StdDraw.picture(.1, .025, "source" + java.io.File.separator + "Left.jpg");		
	}
	/**
	 * Draws the current value of BPM
	 */
	public void drawInfo() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(.5, .01, .1, .02);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(.5, 0.01, "BPM: "+bpm);
	}

	/**
	 * No longer used. Just use default image that is drawn at start
	 * Would be necessary if I was to include the load function
	 */
//	public void drawSquares() {
//		int color;
//		for (int x = 0; x < 16; x++) {
//			for (int y = 0; y < 16; y++) {
//				color = model.getColor(x, y);
//				if (color == 0) {
//					StdDraw.setPenColor(StdDraw.BLACK);
//				}
//				if (color == 1) {
//					StdDraw.setPenColor(StdDraw.WHITE);
//				}
//				if (color == 2) {
//					StdDraw.setPenColor(StdDraw.YELLOW);
//				}
//				if (color == 2) {
//					StdDraw.setPenColor(StdDraw.BLUE);
//				}
//				StdDraw.filledSquare(.125 + (x * .05), .125 + (y * .05), .0245);
//				StdDraw.setPenColor(StdDraw.WHITE);
//			}
//		}
//	}

	public void cycleArray() {
		for (int y = 0; y < 16; y++) {
			if (model.getOn(col, y)) {
				int c = model.getColor(col, y);
				play(c, y);
			}
		}
		if (col < 15) {
			col++;
		} else {
			col = 0;
		}
	}

	public void play(int i, int y) {
		/**
		 * Starts a new TonePlayer thread from the appropriate TonePlayer array
		 * This use of multithreading is what allows this program to work The
		 * StdAudio library was far too inefficient to use as it led to constant
		 * freezes rendering the program unusable.
		 */
		if (i == 1) {
			new Thread(saws[y]).start();
		}
		if (i == 2) {
			new Thread(pianos[y]).start();
		}
		if (i == 3) {
			new Thread(plucks[y]).start();
		}
	}
}
