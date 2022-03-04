/*
 * File: Target.java
 * Name: Luka Loria
 * Section Leader: Mariam tkesheladze
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	
	double C1 = 72;
	double C2 = 1.65 * C1 / 2.54;
	double C3 = 0.76 * C1 / 2.54;
	
	public void run() {
		drawCircleOne();
		drawCircleTwo();
		drawCircleThree();
		
	}
	private void drawCircleOne() {
		GOval CircleOne = new GOval ((getWidth() - C1) / 2 , (getHeight() - C1) / 2 , C1 , C1);
		CircleOne.setFilled(true);
		CircleOne.setFillColor(Color.RED);
	    add(CircleOne);
	}
	
	private void drawCircleTwo() {
		GOval CircleTwo = new GOval ((getWidth() - C2) / 2 , (getHeight() - C2) / 2 , C2 , C2);
		CircleTwo.setFilled(true);
		CircleTwo.setFillColor(Color.WHITE);
	    add(CircleTwo);
	}
	
	private void drawCircleThree() {
		GOval CircleThree = new GOval ((getWidth() - C3) / 2 , (getHeight() - C3) / 2 , C3 , C3);
		CircleThree.setFilled(true);
		CircleThree.setFillColor(Color.RED);
	    add(CircleThree);
	}
}
