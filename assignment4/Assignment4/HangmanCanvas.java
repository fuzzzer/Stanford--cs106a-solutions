/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.util.ErrorException;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		rememberMistakes = "";
		add(scaffold);
		add(beam);
		add(rope);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {// this method displayes hidden word on graphical canvas.
		if(guessedString != null) remove(guessedString);
		guessedString = new GLabel(word , noteX , noteY);
		guessedString.setFont("ITALIC-30");
		add(guessedString);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {// this method adds hangman parts and wrong guesses on the graphical canvas.
		rememberMistakes = letter + rememberMistakes;
		if(badTimes != null) remove(badTimes);
	    badTimes = new GLabel(rememberMistakes , noteX , noteY + diffBetweenNotes);
		badTimes.setFont("SansSerif-18");
		add(badTimes);
		addNextObj(rememberMistakes.length());
	}

	
	public void addNextObj(int index) {//this method adds objects if you give it a number between 1-8
			if( index == 1 ) add(head);
			if( index == 2 ) add(body);
			if( index == 3 ) {
				add(leftUpperArm); 
				add(leftLowerArm);
			}
			if( index == 4 ) {
				add(rightUpperArm); 
				add(rightLowerArm);
			}
			if( index == 5 ) {
				add(leftLeg); 
				add(leftHip);
			}
			if( index == 6 ) {
				add(rightLeg);
				add(rightHip);
			}
			if( index == 7 ) add(leftFoot); 
			if( index == 8 ) add(rightFoot);
	}
	
	
	

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
	private static final int startingX = 100;
	private static final int startingY = 450;
	private static final int noteX = 30;
	private static final int noteY = 550;
	private static final int diffBetweenNotes = 50;
	
	private GLabel guessedString;
	private GLabel badTimes;
	private String rememberMistakes = "";
	
	//these are graphical objects waiting to be added on canvas.
	private GLine scaffold = new GLine(startingX , startingY , startingX , startingY - SCAFFOLD_HEIGHT);
	private GLine beam = new GLine( scaffold.getEndPoint().getX() , scaffold.getEndPoint().getY() , scaffold.getEndPoint().getX() + BEAM_LENGTH , scaffold.getEndPoint().getY());
	private GLine rope = new GLine( beam.getEndPoint().getX() , beam.getEndPoint().getY() , beam.getEndPoint().getX() , beam.getEndPoint().getY() + ROPE_LENGTH);
	private GOval head = new GOval( rope.getEndPoint().getX() - HEAD_RADIUS , rope.getEndPoint().getY() , 2 * HEAD_RADIUS , 2 * HEAD_RADIUS);
	private GLine body = new GLine( rope.getEndPoint().getX() , rope.getEndPoint().getY() + 2 * HEAD_RADIUS , rope.getEndPoint().getX() , rope.getEndPoint().getY() + 2 * HEAD_RADIUS +BODY_LENGTH);
	private GLine rightUpperArm = new GLine( body.getStartPoint().getX() , body.getStartPoint().getY() + ARM_OFFSET_FROM_HEAD , body.getStartPoint().getX() + UPPER_ARM_LENGTH , body.getStartPoint().getY() + ARM_OFFSET_FROM_HEAD);
	private GLine rightLowerArm = new GLine( rightUpperArm.getEndPoint().getX() , rightUpperArm.getEndPoint().getY() , rightUpperArm.getEndPoint().getX() , rightUpperArm.getEndPoint().getY() + LOWER_ARM_LENGTH);
	private GLine leftUpperArm = new GLine( body.getStartPoint().getX() , body.getStartPoint().getY() + ARM_OFFSET_FROM_HEAD , body.getStartPoint().getX() - UPPER_ARM_LENGTH , body.getStartPoint().getY() + ARM_OFFSET_FROM_HEAD);
	private GLine leftLowerArm = new GLine( leftUpperArm.getEndPoint().getX() , leftUpperArm.getEndPoint().getY() , leftUpperArm.getEndPoint().getX() , leftUpperArm.getEndPoint().getY() + LOWER_ARM_LENGTH);
	private GLine rightHip = new GLine( body.getEndPoint().getX() , body.getEndPoint().getY() , body.getEndPoint().getX() + HIP_WIDTH / 2 , body.getEndPoint().getY() );
	private GLine leftHip = new GLine( body.getEndPoint().getX() , body.getEndPoint().getY() , body.getEndPoint().getX() - HIP_WIDTH / 2 , body.getEndPoint().getY() );
	private GLine rightLeg = new GLine( rightHip.getEndPoint().getX() , rightHip.getEndPoint().getY() , rightHip.getEndPoint().getX() , rightHip.getEndPoint().getY() + LEG_LENGTH);
	private GLine leftLeg = new GLine( leftHip.getEndPoint().getX() , leftHip.getEndPoint().getY() , leftHip.getEndPoint().getX() , leftHip.getEndPoint().getY() + LEG_LENGTH);
	private GLine rightFoot = new GLine( rightLeg.getEndPoint().getX() , rightLeg.getEndPoint().getY() , rightLeg.getEndPoint().getX() + FOOT_LENGTH , rightLeg.getEndPoint().getY());
	private GLine leftFoot = new GLine( leftLeg.getEndPoint().getX() , leftLeg.getEndPoint().getY() , leftLeg.getEndPoint().getX() - FOOT_LENGTH , leftLeg.getEndPoint().getY());

}
