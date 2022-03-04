/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import java.util.ArrayList;

public class HangmanLexicon {
	
	
	private int lineCounter;
	ArrayList<String> words = new ArrayList<String>();
	
	
	
	private void createArrayList() {// this method creates a list of words from HangmanLexicon.txt
		try {
			BufferedReader rd  = new BufferedReader(new FileReader("HangmanLexicon.txt")); 
			String line = rd.readLine();
			while(line != null) {
					lineCounter++;
					words.add(line);
					line = rd.readLine();
			}
		} catch (Exception e) {
		}
	}
	
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		lineCounter = 0;
		createArrayList();
		return lineCounter;
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		words.clear();
		createArrayList();
		return words.get(index);
	}
}
