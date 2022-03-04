/*
 * File: Breakout.java
 * -------------------
 * Name: Luka Loria
 * Section Leader: Mariam Tkesheladze
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	private static final int delay = 10;
	
	private static final double startingYSpeed = 3.0;
	
	private static final int delayBetweenRounds = 1500;
	
	
	//instance variables
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GLabel notification;
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private double X;
	private double Y;
	private GPoint point1;
	private GPoint point2;
	private GObject collidingObj;
	
	private Boolean click = false; // this variable is used to tell the program when to start the actual game
	private int roundCount = 0;
	private double Vx = rgen.nextDouble( 1.0, 3.0);
	private double Vy = startingYSpeed;
	private int totalBricks = NBRICKS_PER_ROW * NBRICK_ROWS;
	
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	
	GLabel scores;
	


/** Runs the Breakout program. */
	public void run() {
		addMouseListeners();
		welcome(); // this method says welcome at the start
		brickSetup(); // this method arranges the brick wall
		gamingSetup(); // this method arranges paddle or ball and after the game clears the canvas
		determineBallTrajectory(); // this method will define on which side will the ball go at the start of the round left or right
		startGame(); // this method lets you play the game until you lose 3 life
		properEnding(); // this method will tell you either you won, or you lost
	}
	
	public void mousePressed(MouseEvent e) { 
		click = true; // after setting value to true ball starts moving and paddle is movable
	}
	
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		if(click) mouseMover(x); 
	}
	
	private void startGame() { // this method lets you play the game until you lose 3 life
		while(roundCount < NTURNS) { // the loop only will be active until you either lose 3 times or break all the bricks
			if(totalBricks > 0) {
				lifeExpectancy(NTURNS-roundCount); 
				action(); // this method moves the ball and tells it what to do when the collision occurs, also counts how many round have you played and also controls totalBrick value in order to stop the game if you won by setting the roundCount value to 3
				gamingSetup(); // this method arranges paddle or ball and after the round clears the canvas
			}
		}
	}
	
	private void action() { // this method moves the ball and tells it what to do when the collision occurs, also counts how many round have you played and also controls totalBrick value in order to stop the game if you won by setting the roundCount value to 3
		if(click) { // action starts only if a player presses a mouse
			while(true) {
				moveBall(); // this method makes the ball move accordingly
				checkForCollision(); // this method changes the ball directions if necessary and also removes the brick if the ball collides with it
				
				if( Y + 2 * BALL_RADIUS > getHeight()) {
					roundCount++;
					   break;
				}
				if(totalBricks == 0) {
					roundCount = 3;
					break;
				}
				pause(delay);
			}
			click = false; // after the loop click value is set to false, so a player wont be able to move play until the mouse is pressed again
		}
	}
	
	private void brickSetup() { // this method will Create a wall of bricks
		for(int i = 0 ; i < NBRICK_ROWS ; i++) {
			for(int j = 0 ; j < NBRICKS_PER_ROW ; j++) { // this for loop will create a single row of bricks, colored accordingly
				brick = new GRect( 0 + j * (BRICK_WIDTH + BRICK_SEP) , BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP) , BRICK_WIDTH , BRICK_HEIGHT );
				brick.setFilled(true);
				if( i == 0 || i == 1) {
					brick.setFillColor(Color.RED);
					brick.setColor(Color.RED);
				}
				if( i == 2 || i == 3) {
					brick.setFillColor(Color.ORANGE);
					brick.setColor(Color.ORANGE);
				}
				if( i == 4 || i == 5) {
					brick.setFillColor(Color.YELLOW);
					brick.setColor(Color.YELLOW);
				}
				if( i == 6 || i == 7) {
					brick.setFillColor(Color.GREEN);
					brick.setColor(Color.GREEN);
				}
				if( i == 8 || i == 9) {
					brick.setFillColor(Color.CYAN);
					brick.setColor(Color.CYAN);
				}
				add(brick);
			}
		}
	}
	
	private void gamingSetup() { // this method arranges paddle or ball and after the round clears the canvas
		if(roundCount < NTURNS){
			clearAfterLastGame(); // this method removes paddle and ball for the starting setup to be possible
			paddleSetup(); // this method sets up the paddle
			ballSetup(); // this method sets up the ball
			pause(delayBetweenRounds / 3); // a little delay is needed to make lifeExpactancy notification twinkle
		} else {
			removeAll(); // this option will only be used if the game is over, so the canvas will be cleared
		}
		
		
	}
	
	private void determineBallTrajectory() { 
		if(rgen.nextBoolean(0.5)) Vx = - Vx;
	}
	
	private void clearAfterLastGame() { // this method removes paddle and ball for the starting setup to be possible
		if(ball != null) remove(ball);
		if(paddle != null) remove(paddle);
	}

	
	private void paddleSetup() { // this method sets up the paddle
		paddle = new GRect( ( getWidth() - PADDLE_WIDTH ) / 2 , getHeight() -  PADDLE_Y_OFFSET - PADDLE_HEIGHT , PADDLE_WIDTH , PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
	}
	
	private void ballSetup() { // this method sets up the ball
		ball = new GOval( getWidth() / 2 - BALL_RADIUS  , getHeight() / 2 - BALL_RADIUS , 2 * BALL_RADIUS , 2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
	
	private void mouseMover(int x) { // this method mouseMover(int x) is making possible for you to move the paddle
		if( getWidth() > x + PADDLE_WIDTH / 2 && x - PADDLE_WIDTH / 2 > 0) {
			paddle.setLocation( x - PADDLE_WIDTH / 2 , getHeight() -  PADDLE_Y_OFFSET - PADDLE_HEIGHT );
		}
	}
	
	private void moveBall() { // this method makes sure the ball will move
		ball.move( Vx , Vy);
	}
	
	private void collision(GObject collidingObj) {
		bounceClip.play();
		this.remove(collidingObj);
		totalBricks --;

		System.out.println("check " + Vy);
		System.out.println("check " + (startingYSpeed + Math.round(100-totalBricks)/5)* Math.abs(Vy)/Vy);
		System.out.println("ball: " + ball.getY());
		
		Vy =(startingYSpeed + Math.round(100-totalBricks)/5)* Math.abs(Vy)/Vy ; // (startingYSpeed + Math.round(100-totalBricks)/1)* Math.abs(Vy)/Vy;
		
		if(scores != null) remove(scores);
		scores = new GLabel("bricks destoroyed: " + (100-totalBricks));
		add(scores, 10, 10);
	}
	
	private void checkForCollision() { // this method changes the ball directions if necessary and also removes the brick if the ball collides with it
		X = ball.getX();
		Y = ball.getY();
		
		wallCollision();
		paddleCollision();
		topAndBrickCollision(); // this method checks the intersection of the balls North part and a brick, changes direction of the ball, removes the brick if collision occurs and also counts the number of bricks
		rightAndBrickCollision(); // this method checks the intersection of the balls East part and a brick, changes direction of the ball, removes the brick if collision occurs and also counts the number of bricks
		bottomAndBrickCollision(); // this method checks the intersection of the balls South part and a brick, changes direction of the ball, removes the brick if collision occurs and also counts the number of bricks
		leftAndBrickCollision(); // this method checks the intersection of the balls West part and a brick, changes direction of the ball, removes the brick if collision occurs and also counts the number of bricks
		
	}
	
	private void wallCollision() { // this method makes sure that ball will change direction if it collides with left, right or upper wall
		if( X + 2 * BALL_RADIUS > getWidth()) Vx = -Vx;
		if( X < 0 ) Vx = -Vx;
		if( Y < 0 ) Vy = -Vy;
	}
	
	private void paddleCollision() { // this method makes sure that ball will change direction if it collides with paddle
		point1 = new GPoint( X , Y + 2 * BALL_RADIUS);
		point2 = new GPoint( X + 2 * BALL_RADIUS , Y + 2 * BALL_RADIUS);
		if( getElementAt(point1) == paddle || getElementAt(point2) == paddle ) {
			System.out.println("collision to paddle stage 1");
			if( Y + 2 * BALL_RADIUS >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT ) {
				Vy = -Vy;
				bounceClip.play();
				System.out.println("collision to paddle stage 2");
			}
		}
	}
	
	// following 4B method check the intersection of the ball and a brick, changes direction of the ball, removes the brick if collision occurs and also counts the number of bricks
	private void topAndBrickCollision() { // 1B
		point1 = new GPoint( X + BALL_RADIUS, Y - 1);
		if( getElementAt(point1) != paddle && getElementAt(point1) != null ) {
			Vy = - Vy;
			collidingObj = getElementAt(point1);
			collision(collidingObj);
		}
	}
	
	
	private void rightAndBrickCollision() { // 2B
		point1 = new GPoint( X + 2 * BALL_RADIUS + 1 , Y + BALL_RADIUS);
		if( getElementAt(point1) != paddle && getElementAt(point1) != null) {
			Vx = - Vx;
			collidingObj = getElementAt(point1);
			collision(collidingObj);
			
		}
	}
	
	private void bottomAndBrickCollision() { // 3B
		point1 = new GPoint( X + BALL_RADIUS , Y + 2 * BALL_RADIUS + 1 );
		if( getElementAt(point1) != paddle && getElementAt(point1) != null ) {
			Vy = -Vy;
			collidingObj = getElementAt(point1);
			collision(collidingObj);
		}
	}
	
	private void leftAndBrickCollision() { // 4B
		point1 = new GPoint( X - 1 , Y + BALL_RADIUS);
		if( getElementAt(point1) != paddle && getElementAt(point1) != null ) {
			Vx = - Vx;
			collidingObj = getElementAt(point1);
			collision(collidingObj);
		}
	}
	
	
	// after these point you'll meet only notification methods 
	private void welcome() { // this method sets up the welcome notification
	    notification = new GLabel ("Welcome");
		notification.setFont("ITALIC-54");
		notification.setColor(Color.BLUE);
		add(notification , (getWidth() - notification.getWidth()) / 2 , (getHeight() - notification.getAscent()) /  2 );
		pause(delayBetweenRounds);
		remove(notification);
	}
	
	private void lifeExpectancy(int n) { // this method lifeExpectancy(NTURNS-roundCount) returns how many life have you got and adds it to the canvas
		notification = new GLabel (n + " LIFE LEFT");
		notification.setFont("PLAIN-24");
		notification.setColor(rgen.nextColor());
		add(notification , (getWidth() - notification.getWidth()) / 2 , (getHeight() - notification.getAscent()) /  2 );
		pause(delayBetweenRounds);
		remove(notification);
	}
	
	private void properEnding() {
		if(totalBricks > 0) {
			GameOver();
		} else {
			youWin();
		}
	}
	
	private void GameOver() { // this method sets up twinkling "GameOver" notification, which indicates that a player lost a game
		pause(delayBetweenRounds / 4);
		notification = new GLabel ("Game Over");
		notification.setFont("ITALIC-54");
		notification.setColor(Color.RED);
		fireFly();
	}
	
	private void youWin() {
		pause(delayBetweenRounds / 4); // this method sets up twinkling "YOU WIN" notification
		notification = new GLabel ("YOU WIN");
		notification.setFont("ITALIC-54");
		notification.setColor(Color.GREEN);
		fireFly();
	}
	
	private void fireFly() { // this method adds notification and makes it twinkle
		for(int i = 0 ; i < NTURNS ; i++){
			pause(delayBetweenRounds / 3);
			add(notification , (getWidth() - notification.getWidth()) / 2 , (getHeight() - notification.getAscent()) /  2 );
			pause(delayBetweenRounds / 3);
			remove(notification);
			removeAll();
		}
		add(notification , (getWidth() - notification.getWidth()) / 2 , (getHeight() - notification.getAscent()) /  2 );
	}
}
