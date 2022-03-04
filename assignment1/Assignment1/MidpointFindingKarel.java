/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		findMiddlePoint();
	}
	
	public void findMiddlePoint() {
		goAtTheTopCell();
		putBeeperIfKnightMoveExists();
		while(beepersPresent()) {
			moveLikeKnight();
		}
		putBeeper();
	}
	
	private void goAtTheTopCell() {
		turnLeft();//looking North
		while(frontIsClear()) {
			move();
		}
		turnAround();//looking South, at top left corner
	}
	
	private void moveLikeKnight() {
		pickBeeper();
		move();
		move();
		turnLeft();//looking East
		move();
		turnRight();//looking South
		putBeeperIfKnightMoveExists();
	}
	private void putBeeperIfKnightMoveExists() {
		// if KnightMove doesn't exist this will make sure that karel stops at the bottom wall
		if(frontIsClear()) {
			move();
			if(frontIsClear()) {
				turnAround();//looking North
				move();
				turnAround();//looking South
				putBeeper();
			}
		}
	}
}
