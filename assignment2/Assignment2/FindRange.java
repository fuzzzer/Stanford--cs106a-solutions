/*
 * File: FindRange.java
 * Name: Luka Loria
 * Section Leader: Mariam tkesheladze
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	int M = 0;
	int N = 0;

	public void run() {
		println("This program finds the largest and smallest numbers.");
		while(true) {
			int P = readInt();
			if( P == 0 ) {
				if ( M == 0 && N == 0  ) {
					println("You entered symbol that makes the program end.");
					break;
				} else {
					println("smallest: " + N);
					println("largest: " + M);
					break;
				}
			} 	 
				M = getMax(P, M);
				N = getMin(P, M);
		}
		
	}
	
	private int getMax(int a, int b) {
		if(a<b) {
			int Max = b;
			return Max;
		} else {
			int Max = a;
			return Max;
		}
	}
		
		
    private int getMin(int a, int b) {
			if(a<b) {
				int Min = a;
				return Min;
			} else {
				int Min = b;
				return Min;
			}
	}
	

}

