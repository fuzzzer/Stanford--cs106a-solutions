 /* File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		cutInPeaces(line);
		createScoreArray();
	}
	
	private void cutInPeaces(String line) {
		nameLength = line.indexOf(" ");
		name = line.substring(0, nameLength);
		scores = line.substring(nameLength+1);
	}
	
	private void createScoreArray() {
		String[] splitData = scores.split(" ");
		integerScores = new int[NDECADES];
		for(int i=0 ; i<splitData.length ; i++) {
			integerScores[i] = Integer.parseInt(splitData[i]);
		}
	}
	
	private String readAbleInfo() {
		String result = "";
		for(int i=0 ; i<integerScores.length ; i++) {
			if(result.equals("")) {
				result = result + integerScores[i];
			} else {
				result = result + ", " + integerScores[i];
			}
		}
		result = name + " [" + result + "]";
		return result;
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/* Method: getRank(decade) */
/**a
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return integerScores[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		return readAbleInfo();
	}
	
	
	//instance variables
	int nameLength;
	String scores;
	String name;
	int[] integerScores;
	
}

