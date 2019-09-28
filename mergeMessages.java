/**
 * Ruifeng Wang
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package rwang_p2EC;

/**
 * @author Ruifeng Wang
 * @version 1.0
 * Interleaves multiple messages together, alternating words
 */
public class mergeMessages {
	
	/**
	 *	Nodes that store each word of the interleaved message. 
	 *	Each node will store one word and one number.
	 */
	private class Node {
		
		private int value; // Integer value of the node
		private String word; // String value of the node
		private Node next; // Reference to the next node
		
		/**
		 * Constructor 
		 * @param val		The integer value to store in the node
		 * @param word		The String value to store in the node
		 * @param n			Reference to successor node
		 */
		private Node(int val, String word, Node n) {
			value = val;
			this.word = word;
			next = n;
		}
		
		/**
		 * Constructor
		 * @param val	The integer value to store in the node
		 * @param word	The String value to store in the node
		 */
		private Node(int val, String word) {
			this(val, word, null);
		}
	}
	
	private Node first; // List head
	private String[] words; // Array to hold the words
	
	/**
	 * Constructor
	 */
	public mergeMessages() {
		first = null;
	}
	
	/**
	 * Allows the list to be printed
	 * @return The string of the full message
	 */
	public String toString() {
		Node ref = first;
		String s = "";
		while (ref != null) {
			s += ref.word + " ";
			ref = ref.next;
		}
		return s;
	}
	
	/**
	 * Iterates through the words of a sentence, passing each word to be added
	 * @param sentence		sentence to be added	
	 * @param fileNumber	file number to determine word positions
	 */
	public void scanFile(String sentence, int fileNumber) {
		words = sentence.split(" ");
		for (int i = 0; i < words.length; i++) {
			addInOrder(words[i], (i*2)+fileNumber);
		}
	}
	
	/**
	 * Adds values to list in order
	 * @param word	The word to add to the node
	 * @param val	The integer value to add to the node
	 */
	private void addInOrder(String word, int val) {
		if (isEmpty() || val < first.value) {
			first = new Node(val, word, first);
		} else {
			Node ref = first;
			while (ref.next != null && val > ref.next.value) {
				ref = ref.next;
			}
			ref.next = new Node(val, word, ref.next);
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
