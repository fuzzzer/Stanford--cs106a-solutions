/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
    public void run() {
    	CollectNewspaper();
    }
    
    
	public void CollectNewspaper() {
		goToTargetCell();
		getNewspaper();
		goToStartingCell();
	}
	private void goToTargetCell() {
		move();//at (4,4)
		move();//at (5,4)
		turnRight();//looking South
		move();//at (5,3)
		turnLeft();//looking East
		move();//at (6,3)
	}
	private void getNewspaper() {
		pickBeeper();
	}
	private void goToStartingCell() {
		turnAround();//looking West
		move();//at (5,3)
		turnRight();//looking North
		move();//at (5,4)
		turnLeft();//looking West
		move();//at (4,4)
		move();//at (3,4)
		turnAround();//looking East
		
	}

}
