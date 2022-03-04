/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	
	
	public void init() {
		
		graph = new NameSurferGraph(); 
		add(graph);
		
		fill = new NameSurferDataBase(NAMES_DATA_FILE);
		
		textField = new JTextField(10);
		add(textField, SOUTH);
		
		JButton graphButton = new JButton("Graph");
		add(graphButton, SOUTH);
		
		JButton delete = new JButton("Delete");
		add(delete, SOUTH);
		
		JButton clearButton = new JButton("Clear");
		add(clearButton, SOUTH);
		
		addActionListeners();
		textField.addActionListener(this);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		String text = textField.getText();
		text = accuratelyWritten(text);
		if(e.getSource() == textField || cmd.equals("Graph")){
			if (fill.findEntry(text) != null) {
				graph.addEntry(fill.findEntry(text));
			}
		}
		if(cmd.equals("Clear")) {
			graph.clear();
		}
		if(cmd.equals("Delete")) {
			graph.deleteNameStatistics(text);
		}
		graph.update();
	}
	
	private String accuratelyWritten(String text) {
		String firstLetter = text.substring(0, 1);
		String secondPart = text.substring(1);
		firstLetter = firstLetter.toUpperCase();
		secondPart = secondPart.trim();
		secondPart = secondPart.toLowerCase();
		
		String result = firstLetter + secondPart;
		return result;
		
	}
	
	// instance variables
	JTextField textField;
	NameSurferDataBase fill;
	private NameSurferGraph graph;

}
