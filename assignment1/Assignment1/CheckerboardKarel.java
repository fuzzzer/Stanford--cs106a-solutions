/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		drawTheCheckerBoard();
	}
	
	public void drawTheCheckerBoard() {
		fillTheDiagonal();
		if (leftIsClear()) {
			while(beepersPresent()) {
				goNorth();
				fillTheDiagonal();
				checkIfPreviousNorthCellIsClear();
			}
		}
		goAtTheStartingPosition();
		if (frontIsClear()) {
			while(beepersPresent()) {
				goEast();
				fillTheDiagonal();
				checkIfPreviousWestCellIsClear();
			}
		}
	}
	
	private void fillTheDiagonal() {
		putBeeper();
		turnLeft();//looking North
		while(frontIsClear()&&rightIsClear()) {
			move();
			turnRight();//looking East
			move();
			putBeeper();
			turnLeft();//looking North
		}
		goAtTheBeginingOfThisDiagonal();
	}
	
	private void goAtTheBeginingOfThisDiagonal() {
		turnAround();//looking South
		while(rightIsClear()&&frontIsClear()) {
			move();
			turnRight();//looking west
			move();
			turnLeft();//looking South
		}
		turnLeft();//looking East
	}
	
	private void goNorth() {
		turnLeft();//looking North
		if(frontIsClear()) {
			move();
			if(frontIsClear()) {
				move();
			}
		}
		turnRight();//looking East
	}
	
	private void goAtTheStartingPosition() {
		turnRight();//looking South
		while(frontIsClear()) {
			move();
		}
		turnLeft();//looking East
	}
	
	private void goEast() {
		if(frontIsClear()) {
			move();
			if(frontIsClear()) {
				move();
			}
		}
	}
	private void checkIfPreviousNorthCellIsClear() {
		if(leftIsBlocked()) {
			turnRight();//looking South
			move();
			turnLeft();//looking East
			if(beepersPresent()) {
				turnLeft();//looking North
				move();
				pickBeeper();
				turnRight();//looking East
			}
		}
	}
	
	private void checkIfPreviousWestCellIsClear() {
		if(frontIsBlocked()) {
			turnAround();//looking West
			move();
			turnAround();//looking East
			if(beepersPresent()) {
				move();
				pickBeeper();
			}
		}
	}
}
