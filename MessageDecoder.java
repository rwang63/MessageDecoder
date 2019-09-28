/**
 * Ruifeng Wang
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */

package rwang_p2EC;

import java.io.*;
import java.util.Scanner;

/**
 * @author Ruifeng Wang
 * @version 1.0
 * Decodes the message by organizing the letters in order as dictated on
 * the file 
 */
public class MessageDecoder {

	/**
	 *	Nodes that store each letter of the message. Each node will store one
	 *	letter and one number.
	 */
	private class Node {
		private int value; // Integer value of the node
		private char letter; // Character to be stored in the node
		private Node next; // Reference to the next node
	
		/**
		 * Constructor 
		 * @param val		The integer value to store in the node
		 * @param letter	The character value to store in the node
		 * @param n			Reference to successor node
		 */
		private Node(int val, char letter, Node n) {
			value = val;
			this.letter = letter;
			next = n;
		}
		
		/**
		 * Constructor
		 * @param val	The integer value to store in the node
		 * @param let	The character value to store in the node
		 */
		private Node(int val, char let) {
			this(val, let, null);
		}
	}
	
	private Node first; // List head
	
	/**
	 * Constructor
	 */
	public MessageDecoder() {
		first = null;
	}
	
	/**
	 * Allows the list to be printed
	 * @return The string of the full message
	 */
	public String getPlainTextMessage() {
		Node ref = first;
		String s = "";
		while (ref != null) {
			s += ref.letter;
			ref = ref.next;
		}
		return s;
	}
	
	/**
	 * Checks if file is malformed. If the file is formatted correctly, the 
	 * file is processed. 
	 * @param file	name of the file to check	
	 * @return returns true if file is valid, false if malformed
	 * @throws FileNotFoundException throws exception if file not found
	 */
	public boolean scanFile(String file) throws FileNotFoundException {
		File dataFile = new File(file);
		Scanner inputFile = new Scanner(dataFile);
		while(inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			// Checks if line is too short or if the number is negative / not 
			// a number
			if (line.length() <= 2 || !Character.isDigit(line.charAt(2))) {
				inputFile.close();
				return false;
			};
			// Captures the character and the integer
			char c = line.charAt(0);
		    int item = Integer.valueOf((line.substring(2,line.length())));
		    
		    addInOrder(c, item);
		    
		    // Checks to make sure 2 nodes do not have the same value after
		    // the list is created
		    Node ref = first;
			while (ref.next != null) {
				if (ref.value == ref.next.value) {
					inputFile.close();
					return false;
				} else {
					ref = ref.next;
				}
			}
		}
		inputFile.close();
		return true;
	}
	
	/**
	 * Adds values to list in order
	 * @param let	The letter to add to the node
	 * @param val	The value to add to the node
	 */
	private void addInOrder(char let, int val) {
		if (isEmpty() || val < first.value) {
			first = new Node(val, let, first);
		} else {
			Node ref = first;
			while (ref.next != null && val > ref.next.value) {
				ref = ref.next;
			}
			ref.next = new Node(val, let, ref.next);
		}
	}
	
	/**
	 * Checks if the list is empty
	 * @return	Returns true if list is empty, false otherwise 
	 */
	private boolean isEmpty() {
		return first == null;
	}
	
}
