/*
 * File: Pyramid.java
 * Name: Luka Loria
 * Section Leader: Mariam tkesheladze
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	public void run() {
		for (int i = 1 ; i < (BRICKS_IN_BASE + 1) ; i++ ) {
			for (int j = i - 1 ; j < BRICKS_IN_BASE ; j++ ) {
				GRect blocks = new GRect ((getWidth() - BRICK_WIDTH * BRICKS_IN_BASE - (i - 1) * BRICK_WIDTH ) / 2 + j * BRICK_WIDTH , getHeight() - i * BRICK_HEIGHT , BRICK_WIDTH , BRICK_HEIGHT );
				add(blocks);
			}
		}
	}
}

