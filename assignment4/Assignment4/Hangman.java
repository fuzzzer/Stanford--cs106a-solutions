/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;

import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.awt.event.*;



public class Hangman extends ConsoleProgram {
	
	
	private char guessedChar;
	private int countInstanceOfGuessedChar;
	private String word;
	private String hiddenWord;
	private static final int totalLives = 8;
	private int lifeCounter;
	private int totalGames = 0;
	private int gamesWon = 0;
	
	private HangmanLexicon HangmanLexicon = new HangmanLexicon();
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int wordsInLexicon = HangmanLexicon.getWordCount();
	
	private HangmanCanvas canvas;

    public void run() {
    	this.setSize(1000,1000);
    	println("Welcome to Hangman!");
    	while(true) {
    		canvas.reset();//
    		lifeCounter = totalLives;
        	word = chooseWord();
        	firstGuessSetup();
        	while(!hiddenWord.equals(word) && lifeCounter > 0) {//this loop will make sure that game will last until either player guesses a word or spends all lives
        		letPlayerGuess();
        		setup();
        	}
        	completedGameSetup();
        	totalGames++;
    	}
	}
    
    public void init() {
    	canvas = new HangmanCanvas();
    	add(canvas);
    }
    
    private String chooseWord() {// this method chooses word from HangmanLexicon class
    	int C = rgen.nextInt(0, wordsInLexicon);
    	String word = HangmanLexicon.getWord(C);
    	return word;
    }
    
    private void firstGuessSetup() {// this method arranges notifications until the Player starts guessing the word. if player has already played, this method also informs a player about his/her winning statistics 
    	hiddenWord = hideWord(word);
    	if(totalGames != 0) {
    		println("You have won " + gamesWon + " out of "+ totalGames + ".");
    		if( gamesWon < (double) totalGames / 2) println("Try to improve statistics, play again:");
    		if( gamesWon >= (double) totalGames / 2) println("You guess words brilliantly, play again:");
    	}
    	println("The word now looks like this: " + hiddenWord);
    	println("You have " + lifeCounter + " guesses left");
    	canvas.displayWord(hiddenWord);// this will add hiddenWord on graphical canvas
    }
    
    private String hideWord(String str) {// this method will hide word
    	String result = "";
    	for(int i=0 ; i<str.length() ; i++) {
    		result = result + "-";
    	}
    	return result;
    }
    
    private void setup() {// this method informs a player about his/her guess and lives lift.
        if(countInstanceOfGuessedChar == 0) {
        	println("There are no " + guessedChar + "'s in the word.");
        	canvas.displayWord(hiddenWord);
        } else {
        	println("The guess is correct.");
        	canvas.displayWord(hiddenWord);
        }
    	if(!hiddenWord.equals(word)) {
    		println("The word now looks like this: " + hiddenWord);
    	    println("You have " + lifeCounter + " guesses left");
    	}
        
    }
    
    private void completedGameSetup() {// this method arranges notifications when a player either losses or wins a game. also counts won games.
    	if(hiddenWord.equals(word)) {
    		canvas.displayWord(hiddenWord);
    		println("You guessed the word: " + word);
    		println("You win.");
    		gamesWon++;
    	} else {
    		canvas.displayWord(hiddenWord);
    		println("The word was: " + word);
    		println("You lose.");
    	}
    }
    
    private void letPlayerGuess() {// this player lets a player guess a character in a hidden. also checks the correctness of the guess and makes a player to enter a supported text only. finally this method will check if the entered letter is in the hidden word. 
    	String guessedString = readLine("Your guess: ");
    	guessedString = checkValidity(guessedString);
    	guessedChar = returnUpperLetter(guessedString);
    	checkguess();
    }
    
    private String checkValidity(String guessedString) {// this method makes sure that a string is one letter. If it is not this method makes a player enter a supported string and returns it.
    	while(true) {
    		if(guessedString.length() == 0) {
    			println("You cant guess if you type nothing, so please enter a letter!");
    			guessedString = readLine("Your guess: ");
    		}
    		if(guessedString.length() > 1) {
    			println("You entered long text which is not supported, please enter a letter!");
    			guessedString = readLine("Your guess: ");
    		}
    		if(guessedString.length() == 1 && !Character.isLetter(guessedString.charAt(0))) {
    			println("You entered not supported Symbol, please enter a letter! ");
    			guessedString = readLine("Your guess: ");
    		}
    		if(guessedString.length() == 1 && Character.isLetter(guessedString.charAt(0))) break;
    	}
    	return guessedString;
    }
   
    private char returnUpperLetter(String guessedString) {// this method transforms string into upper case character.
    	char result = guessedString.charAt(0);
    	if(Character.isLowerCase(result)) result = Character.toUpperCase(result);
    	return result;
    }
    
    private void checkguess() {// this method checks if there is a character in a word. If the character is in the word makes it unhidden at every instance. If the character is not in the word reduces lives and adds next graphical object.
    	countInstanceOfGuessedChar = 0;
    	for(int i=0 ; i<word.length() ; i++) {
    		if(word.charAt(i) == guessedChar) {
    			countInstanceOfGuessedChar++;
    			hiddenWord = substituteChar(hiddenWord, word.charAt(i), i);
    		}
    	}
    	if(countInstanceOfGuessedChar == 0) {
    		lifeCounter--;
    		canvas.noteIncorrectGuess(guessedChar);
    	}
    }
    
    private String substituteChar(String str, char ch, int n) {// this method substitutes N-th character in a string with a given character.
    	if(n == 0) str = ch + str.substring(1);
    	if(n > 0 && n < str.length()-1) str = str.substring(0, n) + ch + str.substring(n+1);
    	if(n == str.length()-1) str = str.substring(0, n) + ch;
    	return str;
    }
}
