/*
 * File: Yahtzee.java
 * Section leader: mariam tkesheladze
 * This program will eventually play the Yahtzee game.
 */

import java.util.ArrayList;
import java.io.*;
import java.util.*;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	private topScoreDisplay canvas;
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		setup();// this method will create arrays which will be used to roll dice, store rolled combination in categories and take notice of which category was already filled
		while(round < N_SCORING_CATEGORIES) {
			playerTurn();// this method arranges each iteration of players turn
			player++;
			if(player == nPlayers + 1) {// this if statement will make sure that next round starts when all players roll the dice
				round++;
				player = 1;
			}
		}
		calculateResults();
		// next 3 methods are used to make sure that program implements Top 10 model
		winnerMessage();// this method arranges last message appropriately.
		topScores();// this method updates or creates "TopScores.txt" file
	}
	
	private void playerTurn() {// this method arranges each iteration of players turn
		display.printMessage(playerNames[player - 1] + "'s turn! Click \"Roll Dice\" button to roll the dice.");
		gamesPlayed = 0;// this variable is needed to tell program to roll all dices on the first roll of each players turn.
		display.waitForPlayerToClickRoll(player);
		rollDice();// this method generates dice numbers randomly if dices are selected or if it it the first roll.
		for(gamesPlayed = 1 ; gamesPlayed < 3 ; gamesPlayed++) {
			display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\".");
			display.waitForPlayerToSelectDice();
			rollDice();
			if(!wasDiceSelected) gamesPlayed = 3;
		}
		categorySelectMessage();// this method informs a player to select a category
		againYahtzee();// this method adds bonus 100 points to a player if he/she rolls the yahtzee again
		categorySelect();// this method lets player choose category and calculates score
	}
	
	private void winnerMessage() {// this method informs winner about his/her score and his place in the top 10 player if he/she played well enough
		if(!topPlayersExcist()) display.printMessage("Congratulations, " + winner() + ", you're the winner with a total score of " + highestScore() + "!");
		if(topPlayersExcist()) display.printMessage("Congratulations, " + winner() + ", you're in the Top 10 players on place: " + newTopPlayerRank() + " place with the score " + highestScore() + "!");
	}
	
	private String winner() {// this method returns winners name
		String result = "";
		int winners = 0;
		for(int i=0 ; i<nPlayers ; i++) {
			if(winners == 0 && scores[i][TOTAL-1] == highestScore()) result = result + playerNames[i];
			if(winners > 0 && scores[i][TOTAL-1] == highestScore()) result = result + " and " + playerNames[i];
		}
		return result;
	
	}
	
	private int highestScore() {// this method returns highest score
		int max = scores[0][TOTAL-1];
		for(int i=1 ; i<nPlayers ; i++) {
			max = Math.max(scores[i][TOTAL-1], max);
		}
		return max;
	}
	
	private void calculateResults() {// this method calculates scores after game ends
		while(player <= nPlayers) {
			calculateScore();
			calculateBonus();
			calculateLowerScore();
			calculateTotalScore();
			player++;
		}
		player = 1;
	}
	
	private void calculateScore() {// this method calculates upper score
		for(int category = ONES ; category <= SIXES ; category++) {
			scores[player-1][UPPER_SCORE-1] += scores[player-1][category-1];
		}
		display.updateScorecard(UPPER_SCORE, player, scores[player-1][UPPER_SCORE-1]);
	}
	
	private void calculateBonus() {// this method calculates bonus if a player is eligible for one
		if(scores[player-1][UPPER_SCORE-1] > 63) scores[player-1][UPPER_BONUS-1] = 35;
		display.updateScorecard(UPPER_BONUS, player, scores[player-1][UPPER_BONUS-1]);
	}
	
	private void calculateLowerScore() {// this method calculates lower score
		for(int category = THREE_OF_A_KIND ; category <= CHANCE ; category++) {
			scores[player-1][LOWER_SCORE-1] += scores[player-1][category-1];
		}
		display.updateScorecard(LOWER_SCORE, player, scores[player-1][LOWER_SCORE-1]);
	}
	
	private void calculateTotalScore() {// this method calculates total score
		scores[player-1][TOTAL-1] = scores[player-1][UPPER_SCORE-1] + scores[player-1][UPPER_BONUS-1] + scores[player-1][LOWER_SCORE-1];
		display.updateScorecard(TOTAL, player, scores[player-1][TOTAL-1]);
	}
	
	private void againYahtzee() {// this method adds bonus 100 points to a player if he/she rolls the yahtzee again
		if(showsSameSide(5) && scores[player-1][YAHTZEE-1] > 0) {
			scores[player-1][YAHTZEE-1] += 100;
			display.updateScorecard(YAHTZEE, player, scores[player-1][YAHTZEE-1]);
			display.printMessage("You rolled YAHTZEE again, you get bonus 100 points, Select a category for this roll.");
		}
		
	}
	
	private void categorySelectMessage() {// this method informs a player to select a category or 
		if(showsSameSide(5)) {
			display.printMessage("Yahtzee! Select a category for this roll.");
		} else {
			display.printMessage("Select a category for this roll.");
		}
	}
	
	private void categorySelect() {// this method lets player select appropriate category and then calculates score in that category
		int category = display.waitForPlayerToSelectCategory();
		while(categoryCounter[player-1][category-1] > 0) {
			display.printMessage("Please select a new category");
			category = display.waitForPlayerToSelectCategory();
		}
		scores[player-1][category-1] = sumOfDices(category);// this equality will make sure that scores array will be updated appropriately
		categoryCounter[player-1][category-1] = 1;
		display.updateScorecard(category, player, scores[player-1][category-1]);
	}
	
	private int sumOfDices(int category) {// this method calculates score in each category
		int result = 0;
		switch(category) {
		case ONES: result = calculateOnes(); break;
		case TWOS: result = calculateTwos(); break;
		case THREES: result = calculateThrees(); break;
		case FOURS: result = calculateFours(); break;
		case FIVES: result = calculateFives(); break;
		case SIXES: result = calculateSixes(); break;
		case THREE_OF_A_KIND: result = calculateThreeOfAKind(); break;
		case FOUR_OF_A_KIND: result = calculateFourOfAKind(); break;
		case FULL_HOUSE: result = calculateFullHouse(); break;
		case SMALL_STRAIGHT: result = calculateSmallStraight(); break;
		case LARGE_STRAIGHT: result = calculateLargeStraight(); break;
		case YAHTZEE: result = CalculateYahtzee(); break;
		case CHANCE: result = CalculateChance(); break;
		}
		return result;
	}
	//next 13 methods calculate scores for each category
	private int calculateOnes() {
		int result = 0;
		for(int i=0 ; i<N_DICE ; i++) {
			if(dice[i] == 1) result = result + 1;
		}
		return result;
	}
	
	private int calculateTwos() {
		int result = 0;
		for(int i=0 ; i<N_DICE ; i++) {
			if(dice[i] == 2) result = result + 2;
		}
		return result;
	}
	
	private int calculateThrees() {
		int result = 0;
		for(int i=0 ; i<N_DICE ; i++) {
			if(dice[i] == 3) result = result + 3;
		}
		return result;
	}
	
	private int calculateFours() {
		int result = 0;
		for(int i=0 ; i<N_DICE ; i++) {
			if(dice[i] == 4) result = result + 4;
		}
		return result;
	}
	
	private int calculateFives() {
		int result = 0;
		for(int i=0 ; i<N_DICE ; i++) {
			if(dice[i] == 5) result = result + 5;
		}
		return result;
	}
	
	private int calculateSixes() {
		int result = 0;
		for(int i=0 ; i<N_DICE ; i++) {
			if(dice[i] == 6) result = result + 6;
		}
		return result;
	}
	
	private int calculateThreeOfAKind() {
		int result = 0;
		if(showsSameSide(3)) {
			for(int i=0 ; i<N_DICE ; i++) {
				result = result + dice[i];
			}
		}
		return result;
	}
	
	private int calculateFourOfAKind() {
		int result = 0;
		if(showsSameSide(4)) {
			for(int i=0 ; i<N_DICE ; i++) {
				result = result + dice[i];
			}
		}
		return result;
	}
	
	private int calculateFullHouse() {
		int result = 0;
		if(isFullHouse()) {
			result = 25;
		}
		return result;
	}
	
	private int calculateSmallStraight() {
		int result = 0;
		if(isStraight(4)) {
			result = 30;
		}
		return result;
	}
	
	private int calculateLargeStraight() {
		int result = 0;
		if(isStraight(5)) {
			result = 40;
		}
		return result;
	}
	
	private int CalculateYahtzee() {
		int result = 0;
		if(showsSameSide(5)) {
			result = 50;
		}
		return result;
	}
	
	private int CalculateChance() {
		int result = 0;
		for(int i=0 ; i<N_DICE ; i++) {
			result = result + dice[i];
		}
		return result;
	}
	
	private boolean showsSameSide(int N) {// this boolean returns true if at least N dices show same side
		int countSameSides = 0;
		for(int i=0 ; i<N_DICE ; i++) {
			int temp = 0;
			for(int j=i ; j<N_DICE ; j++) {
				if(dice[i] == dice[j]) temp++;
			}
			countSameSides = Math.max(countSameSides, temp);
		}
		if(countSameSides >= N) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isFullHouse() {// this method returns true if dices show full house
		int[] countInstances = new int[N_DICE];
		for(int i=0 ; i<N_DICE ; i++) {
			for(int j=0 ; j<N_DICE ; j++) {
					if(dice[i] == dice[j]) countInstances[i]++;
			}
		}
		for(int i=0 ; i<N_DICE ; i++) {
			if(countInstances[i] != 3 && countInstances[i] != 2) return false;
		}
		return true;
	}

	
	private boolean isStraight(int N) {// this method checks N length straight
		int[] ascendingOrder = sortInAscendingOrder(dice);
		int maxLengthStraight = 1;
		int temp = 1;
		for(int i=0 ; i<N_DICE-1 ; i++) {
			int lastDiff = ascendingOrder[i+1] - ascendingOrder[i];
			if(lastDiff == 1) {
				temp++;
				maxLengthStraight = Math.max(maxLengthStraight, temp);
			} else {
				temp = 1;
			}
		}
		if(maxLengthStraight >= N) return true;
		return false;
	}
	
	private int[] sortInAscendingOrder(int[] arr) {// this method sorts and returns array in ascending order
		int[] result = new int[arr.length];
		int saved = 0;
		while(saved < arr.length) {
			for(int i=1 ; i<=getMax(arr) ;i++) {
				for(int j=0 ; j<arr.length ; j++) {
					if(i == arr[j]) {
						result[saved] = i;
						saved++;
					}
				}
			}
		}
		return result;
	}
	
	private int getMax(int[] list) {// this method gets max number from an array
		int max = list[0];
		if(list.length > 1) {
			for(int i=1 ; i<list.length ; i++) {
				max = Math.max(max, list[i]);
			}
		}
		return max;
	}

	private void rollDice() {// this method randomly generates numbers for dices to show
		wasDiceSelected = false;
		for(int i=0 ; i<N_DICE ; i++) {
			if(gamesPlayed == 0 || display.isDieSelected(i)) {
				dice[i] = rgen.nextInt(1, 6);
				wasDiceSelected = true;
			}
		}
		display.displayDice(dice);
	}
	
	private void setup() {// this method sets up arrays
		dice = new int[N_DICE];
		scores = new int[nPlayers][N_CATEGORIES];
		categoryCounter = new int[nPlayers][N_CATEGORIES];
	}
	
	//after this point there are only methods to implement top 10 player model
	
	public void init() {// this method arranges canvas before game starts to show players top 10 list
		    oldTopScores();//this method reads old top 10 list from file and prints it
	    	canvas = new topScoreDisplay();// this created new starting canvas from topScoreDisplay.java
	    	add(canvas);
	    	if(topPlayersExcist()) {// this checks if there are previous top players
	    		canvas.draw("Top 10 players are:", 1);
	    	} else {
	    		canvas.draw("Top player list is empty,", 1);
	    		canvas.draw("Be the first top Player", 2);
	    	}
			for(int i=0; i<topPlayers ; i++) {// this for loop adds saved top player names from an array on starting canvas
				if(topScoreArray[i] > 0) {
					String line = (i+1) + ". " + topPlayersArray[i] + " " + topScoreArray[i];
					canvas.draw(line, i+2);
				}
				
			}
			pause(5000);
	    	remove(canvas);
	    }
	
	private boolean topPlayersExcist() {// this method checks if there are top players from previous games
		for(int i=0 ; i<topPlayers ; i++) {
			if(topScoreArray[i] > 0 ) return true;
		}
		return false;
	}
	
	private void oldTopScores() {// this method saves old top scores and additionally prints them on eclipse java canvas (I thought it might have been useful, but I'm not sure if that is actually usefull when you do not run games from eclipse :)
		saveOldTopScores();
		if(topPlayersExcist()) {
			println("Top 10 is:");
			for(int i=0 ; i<topPlayers ; i++) {
				if(topScoreArray[i] > 0 ) println((i+1) + ". " + topPlayersArray[i] + " " + topScoreArray[i]);
			}
		}
	}
	
	
	private int newTopPlayerRank() {// this method returns rank of the new top player
		for(int i=0 ; i<topPlayers ; i++) {
			if(highestScore() > topScoreArray[i]) return i+1;
		}
		return 0;
		
	}
	
	private void topScores() {// if players perform well enough to have a place in top 10 then this method created new top 10 list and updates "TopScore.txt" file
			if(thereAreNewTopPlayers()) {
				createNewTopScoreList();
				updateTopScoreFile();
			}
	}
	
	private void createNewTopScoreList() {// this method updates top players' array and their scores list's array
		for(int k=0 ; k<nPlayers ; k++) {
			for(int i=0 ; i<topPlayers ; i++) {
				if(scores[k][TOTAL-1] > topScoreArray[i]) {
					topScoreArray = moveEveryEntryInteger(topScoreArray , i);
					topPlayersArray = moveEveryEntryString(topPlayersArray , i);
					topScoreArray[i] = scores[k][TOTAL-1];
					topPlayersArray[i] = playerNames[k];
					break;
				}
			}
		}
		sortInDescendingOrderMatched();
	}
	
	private void sortInDescendingOrderMatched() {// this method sorts players and and their scores according to descending order of scores
		int[] tempScoreArray = new int[topPlayers]; 
		String[] tempPlayersArray = new String[topPlayers];
		for(int i=0 ; i<topPlayers ; i++) {
			int temp = topPlayers-1;
			for(int j=0 ; j<topPlayers ; j++) {
				if(topScoreArray[i] > topScoreArray[j]) temp--;
				if(topScoreArray[i] == topScoreArray[j] &&  i>j ) temp--;
			}
			tempScoreArray[temp] = topScoreArray[i];
			tempPlayersArray[temp] = topPlayersArray[i];
		}
		topScoreArray = tempScoreArray;
		topPlayersArray = tempPlayersArray;
	}
	
	private int[] moveEveryEntryInteger(int[] arr , int N) {// this method moves every entry of an Integer array arr by one after it's N'th entry
		int lastStored = arr[N];
		int newStored;
		for(int i=N ; i<arr.length-1 ; i++) {
			newStored = arr[i+1];
			arr[i+1] = lastStored;
			lastStored = newStored;
		}
		return arr;
	}
	
	private String[] moveEveryEntryString(String[] arr , int N) {// this method moves every entry of an String array arr by one after it's N'th entry
		String lastStored = arr[N];
		String newStored;
		for(int i=N ; i<arr.length-1 ; i++) {
			newStored = arr[i+1];
			arr[i+1] = lastStored;
			lastStored = newStored;
		}
		return arr;
	}
	
	private boolean thereAreNewTopPlayers() {// this method checks if there are new top players
		boolean result = false;
		for(int k=0 ; k<nPlayers ; k++) {
			for(int i=0 ; i<topPlayers ; i++) {
				if(scores[k][TOTAL-1] > topScoreArray[i]) result = true;
			}
		}
		return result;
	}
	
	private void updateTopScoreFile() {// this method creates and overWrites over top "TopScores.txt" file. finnaly making it store updated top 10 list
		try {
			PrintWriter wr = new PrintWriter(new FileWriter("TopScores.txt", false));
			for(int i=0 ; i<topPlayers ; i++) {
				if(topScoreArray[i] > 0 ) {
					wr.println(topPlayersArray[i] + " " + topScoreArray[i]);
				}
			}
			wr.close();
		} catch (IOException ex) {
			throw new ErrorException("Unknown Error");
		}
	}
	
	private void saveOldTopScores() {// this method reads old top 10 scores and stores string value in string array and integer values in int array. note that this method only reads and writes special kind of sorted text file
		topScoreArray = new int[topPlayers];
		topPlayersArray = new String[topPlayers];
		try {
			BufferedReader rd = new BufferedReader(new FileReader("TopScores.txt"));
			for(int i=0 ; i<topPlayers ; i++) {
				String line = rd.readLine();
				if(line != null) {
					StringTokenizer tokenizer = new StringTokenizer(line);
					if(tokenizer.hasMoreTokens()) topPlayersArray[i] = tokenizer.nextToken();
					if(tokenizer.hasMoreTokens()) topScoreArray[i] = Integer.parseInt(tokenizer.nextToken());
				}
			}
		rd.close();
		} catch (IOException ex) {
			println("Top player list is empty, Be the first top Player");
		}
	}
	
	
/* constants */
	private static final int topPlayers = 10;
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	
	private int[] dice;
	private int round = 0;
	private int gamesPlayed = 0;
	private int player = 1;
	private int[][] scores;
	private int[][] categoryCounter; 
	private boolean wasDiceSelected;
	
	int[] topScoreArray;
	String[] topPlayersArray;

}