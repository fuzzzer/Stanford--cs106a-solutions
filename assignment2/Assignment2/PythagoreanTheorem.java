/*
 * File: PythagoreanTheorem.java
 * Name: Luka Loria
 * Section Leader: Mariam tkesheladze
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("I will tell you the length of the Hypothenuse");
		int N1 = readInt("Give me the length of the first kathete: ");
		int N2 = readInt("Give me the length of the second kathete: ");
		double S = N1 * N1 + N2 * N2;
		S = Math.sqrt(S);
		int Q = (int) S;
		if( S == Q ) { // this if statement will make sure that if the answer is integer, it won't be written as a double
			int X = Q;
			println("The length of the Hypothenuse is: " + X);
		} else {
			double X = S;
			println("The length of the Hypothenuse is: " + X);
		}	
	}
}