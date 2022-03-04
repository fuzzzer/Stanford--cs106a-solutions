import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.io.*;
import java.util.StringTokenizer;


//this class creates GCanvas and draws strings on it if you pass them with their order n to draw(String str, int n);


public class topScoreDisplay extends GCanvas{
	
	public void clearer() {
		this.removeAll();
	}
	
	public void draw(String str, int n) {
		GLabel line = new GLabel(str);
		line.setFont("ITALIC-24");
		add(line, 0, line.getAscent() * n);
	}
	

}
