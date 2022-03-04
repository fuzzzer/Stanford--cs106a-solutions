/*
 * File: Hailstone.java
 * Name: Luka Loria
 * Section Leader: Mariam tkesheladze
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {

	public void run() {
		int N = readInt("Enter a number: ");
		int L = N;
		int Count = 0;
		if(N == 1) {
			println("The number you entered is already 1.");
		} else {
			for( int i=N ; i != 1  ; i = N) {
				N = doTheThing(N);
				if (L % 2 ==0) {
					println(L + " is even so I take half: " + N);
				} else {
					println(L + "is odd, so I make 3n+1: " + N);
				}
				L = N;
				Count++;
				if( N == 1) {
					println("The process took " + Count + " to reach 1.");
				}
			}
		}
	}
	
	
	private int doTheThing(int a) { //this method treats the variable accordingly
		if(a % 2 == 0) {
			int k = evenInt(a);
			return k;
		} else {
			int k = oddInt(a);
			return k;
		}
	}
	
	
	private int evenInt(int a) {
		int k = a / 2;
		return k;
	}
	
	private int oddInt(int a) {
		int k = a * 3 + 1;
		return k;
	}
	
	
	
}
	
    
