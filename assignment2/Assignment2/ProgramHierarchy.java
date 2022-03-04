/*
 * File: ProgramHierarchy.java
 * Name: Luka Loria
 * Section Leader: Mariam tkesheladze
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	
	private static double rectWidth = 200.0 ;
	private static double rectHeight = 50.0 ;
	
	
	public void run() {
		drawRectangles();
		drawLines();
		addLabels();
	}
	
	private void drawRectangles() {
		GRect rect1 = new GRect ((getWidth() - rectWidth) / 2 , (getHeight() - rectHeight) / 2 - rectHeight , rectWidth , rectHeight ); 
		add(rect1);
		
		GRect rect2 = new GRect ((getWidth() - rectWidth) / 2 - 1.125 * rectWidth , (getHeight() - rectHeight) / 2 + rectHeight , rectWidth , rectHeight );
		add(rect2);
		
		GRect rect3 = new GRect ((getWidth() - rectWidth) / 2 , (getHeight() - rectHeight) / 2 + rectHeight , rectWidth , rectHeight ); 
		add(rect3);
		
		GRect rect4 = new GRect ((getWidth() - rectWidth) / 2 + 1.125 * rectWidth , (getHeight() - rectHeight) / 2 + rectHeight , rectWidth , rectHeight ); 
		add(rect4);
	}
	
	 
	
	 
	
	
	private void drawLines() {
		 double X1 = getWidth() / 2; // thi is middle x the first rectangle
		 double Y1 = (getHeight() - rectHeight) / 2 ; // this is upper y of the first rectangle
		 double X2 = getWidth() / 2 - 1.125 * rectWidth ; // this is middle x the second rectangle
	     double X3 = getWidth() / 2 ; // this is middle x the third rectangle
		 double X4 = getWidth() / 2 + 1.125 * rectWidth ; // this is middle x the forth rectangle
		 
		GLine line1 = new GLine ( X1 , Y1 , X2 , Y1 + rectHeight);
		add(line1);
		
		GLine line2 = new GLine ( X1 , Y1 , X3 , Y1 + rectHeight);
		add(line2);
		
		GLine line3 = new GLine ( X1 , Y1 , X4 , Y1 + rectHeight);
		add(line3);
	}

	
	private void addLabels() {
		 GLabel P = new GLabel("Program");
		 GLabel G = new GLabel("GraphicsProgram");
		 GLabel C = new GLabel("ConsoleProgram");
		 GLabel D = new GLabel("DialogProgram");
		 
		 double X1 = getWidth() / 2; // this is middle x the first rectangle
		 double Y1 = getHeight() / 2 - rectHeight ; // this is middle y of the first rectangle
		 double X2 = getWidth() / 2 - 1.125 * rectWidth ; // this is middle x the second rectangle
	     double X3 = getWidth() / 2 ; // this is middle x the third rectangle
		 double X4 = getWidth() / 2 + 1.125 * rectWidth ; // this is middle x the forth rectangle
		 
		 add(P , X1 - P.getWidth() / 2 , Y1 + P.getAscent() / 2 );
		 add(G , X2 - G.getWidth() / 2 , Y1 + 2 * rectHeight + G.getAscent() / 2 );
		 add(C , X3 - C.getWidth() / 2 , Y1 + 2 * rectHeight + C.getAscent() / 2 );
		 add(D , X4 - D.getWidth() / 2 , Y1 + 2 * rectHeight + D.getAscent() / 2 );
	
	}
		
		
}

