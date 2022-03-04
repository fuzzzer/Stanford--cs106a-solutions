/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		colorCounter = -1;
		nameColors.clear();
		data.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		data.put(entry.getName(), entry);
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		this.removeAll();
		setFontSize();
		addArchitecture();
		addNameStatistics();
	}
	
	private void addArchitecture() {
		drawVerticalLines();
		drawHorizontalLines();
		drawDecadeNames();
	}
	
	private void drawVerticalLines() {
		for(int i=0 ; i<NDECADES ; i++) {
			GLine vertical = new GLine((getWidth() / NDECADES) * i, 0, (getWidth() / NDECADES) * i, getHeight());
			add(vertical);
		}
	}
	
	private void drawHorizontalLines() {
		GLine upper = new GLine( 0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(upper);
		
		GLine lower = new GLine( 0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(lower);
	}
	
	private void drawDecadeNames() {
		for(int i=0 ; i<NDECADES ; i++) {
			int year = START_DECADE + 10 * i;
			GLabel decade = new GLabel(Integer.toString(year), (getWidth() / NDECADES) * i + 1, getHeight() - 1);
			decade.setFont("BOLD-" + fontSize);
			add(decade);
		}
	}
	
	private void addNameStatistics() {
		for(String name : data.keySet()) {
			int lastRank = 0;
			if(!nameColors.keySet().contains(name)) {
				colorCounter = (colorCounter + 1) % 4;
				nameColors.put(name, colorCounter);
			}
			for(int i=0 ; i<NDECADES ; i++) {
				int rank = data.get(name).getRank(i);
				
				if(i>0) {
					GLine decadeGraph = new GLine((getWidth() / NDECADES) * (i-1), GRAPH_MARGIN_SIZE + ((getHeight() - 2 * GRAPH_MARGIN_SIZE) / (MAX_RANK/10)) * (lastRank/10), (getWidth() / NDECADES) * i, GRAPH_MARGIN_SIZE + ((getHeight() - 2 * GRAPH_MARGIN_SIZE) / (MAX_RANK/10)) * (rank/10));
					if(lastRank == 0) decadeGraph.setStartPoint((getWidth() / NDECADES) * (i-1), getHeight() - GRAPH_MARGIN_SIZE );
					if(rank == 0) decadeGraph.setEndPoint((getWidth() / NDECADES) * i, getHeight() - GRAPH_MARGIN_SIZE );
					decadeGraph.setColor(color(nameColors.get(name)));
					add(decadeGraph);
				}
				
				
				GLabel label = new GLabel(name + " " + rank, (getWidth() / NDECADES) * i + 5, GRAPH_MARGIN_SIZE + ((getHeight() - 2 * GRAPH_MARGIN_SIZE) / (MAX_RANK/10)) * (rank/10));
				if((i < NDECADES - 1) && data.get(name).getRank(i+1) > rank) {
					label.setLocation((getWidth() / NDECADES) * i + 5, GRAPH_MARGIN_SIZE + ((getHeight() - 2 * GRAPH_MARGIN_SIZE) / (MAX_RANK/10)) * (rank/10) - label.getAscent() / 2);
				} else {
					label.setLocation((getWidth() / NDECADES) * i + 5, GRAPH_MARGIN_SIZE + ((getHeight() - 2 * GRAPH_MARGIN_SIZE) / (MAX_RANK/10)) * (rank/10) + label.getAscent() / 2);
				}
				if(rank == 0) {
					label.setLabel(name + " *");
					label.setLocation((getWidth() / NDECADES) * i + 5, getHeight() - GRAPH_MARGIN_SIZE);
				}
				label.setFont("BOLD-" + fontSize);
				label.setColor(color(nameColors.get(name)));
				add(label);
				
				lastRank = rank;
			}
		}
	}
	
	public void deleteNameStatistics(String name) {
		colorCounter = (colorCounter - 1) % 4;
		nameColors.remove(name);
		data.remove(name);
	}
	
	
	
	private Color color(int n) {
		switch(n) {
		case 0 : return Color.BLACK;
		case 1 : return Color.RED;
		case 2 : return Color.BLUE;
		case 3 : return Color.GREEN;
		}
		return Color.RED;
	}
	
	private void setFontSize() {
		fontSize = (getWidth() + getHeight()) / 100;
	}
	
	
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	//instance variables
	HashMap<String, NameSurferEntry> data = new HashMap<String, NameSurferEntry>();
	HashMap<String, Integer> nameColors = new HashMap<String, Integer>();
	int check = 0;
	int colorCounter = -1;
	int fontSize;
}
