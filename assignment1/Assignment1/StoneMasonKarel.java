/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
 
	public void run() {
		FixTheBuilding();
	}

	public void FixTheBuilding() {
		while(frontIsClear()) {
			fixColumn();
		    goAtTheBeginingOfTheColumn();
		    moveToTheNextColumn();
		}
		fixColumn();
		goAtTheBeginingOfTheColumn();
	}
	
	private void fixColumn() {
		turnLeft();//looking North
		fillWithBeeper();
		while(frontIsClear()) {
			move();
			fillWithBeeper();
		}
	}
	private void fillWithBeeper() {
		if(noBeepersPresent()) {
			putBeeper();
		}
	}
	private void goAtTheBeginingOfTheColumn() {
		turnAround();//looking South
		while(frontIsClear()) {
			move();
		}
		turnLeft();//looking East
	}
	private void moveToTheNextColumn() {
		for(int i=0; i<4; i++) {
			move();
		}
	}
}
