/**
 * Ruifeng Wang
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package rwang_p2EC;

import java.util.*;
import java.io.*;

/**
 * @author Ruifeng Wang
 * @version 1.0
 * This program asks a user for at least one file, reads the file(s), 
 * decodes the message(s) and interleaves the words for the user. If there 
 * is only one file, the message is just printed normally. The user will be 
 * asked to repeat this as many times as they want. 
 */
public class SecretMessage {

	/**
	 * Main method for decoding and interleaving of secret messages
	 * @param args A string array containing the command line arguments
	 * @throws IOException throws exception on IO error
	 */
	public static void main(String[] args) throws IOException {
		
		String continuePlaying = "yes"; // Storing if user wants to continue
		String addAnotherFile; // Allows the user to keep inputting files
		ArrayList<String> str; // Store file names
		int totalFiles; // Count of how many files have been decoded
		String[] decoded; // Store the decoded sentences
		Scanner keyboard = new Scanner(System.in); // Scanner object
		
		printWelcome();
		while(!continuePlaying.equals("no")) {
			addAnotherFile = "yes";
			totalFiles = 0;
			str = new ArrayList<String>();
			String file = findFile(keyboard);
			MessageDecoder decode = new MessageDecoder();
			if(!decode.scanFile(file)) {
				System.out.println("File is malformed, please try again.");
				decode = new MessageDecoder();
			} else {
				while(!addAnotherFile.isEmpty()) {
					addAnotherFile = findNextFile(keyboard);
					str.add(addAnotherFile); // Adds file name to ArrayList
					totalFiles++; // Counting total number of files
				}
				// Create array to hold decoded sentences based on total 
				// number of files
				decoded = new String[totalFiles]; 
				// Add original decoded sentence to array
				decoded[0] = decode.getPlainTextMessage();
				
				// Decode the rest of the files adding them to the array
				// Throws error and exits if one or more files are malformed
				for(int i = 0; i < str.size() - 1; i++) {
					decode = new MessageDecoder();
					if(!decode.scanFile(str.get(i))) {	
						throw new IllegalArgumentException("One or more files "
								+ "are malformed, please try again.");
					} else {
						decode = new MessageDecoder();
						decode.scanFile(str.get(i));
						decoded[i + 1] = decode.getPlainTextMessage();
					}
				}
				
				mergeMessages merge = new mergeMessages();
				
				for (int i = 0; i < decoded.length; i++) {
					merge.scanFile(decoded[i], i+1);
				}
				
				System.out.println("\nPlain text: " + merge);
	
				System.out.print("\nWould you like to try again? "
						+ "(no to exit): ");
				continuePlaying = keyboard.next();
				keyboard.nextLine();
			}
		}
		keyboard.close();
		System.out.println("\nThank you for using the message decoder.");
	}
	
	/**
	 * Prints simple welcome message to program
	 */
	public static void printWelcome() {
		System.out.println("This program reads an encoded message from a file"
				+ " supplied by the user and");
		System.out.println("displays the contents of the message before it"
				+ " was encoded.");
		System.out.println("The user can enter multiple files and the decoded");
		System.out.println("result words will become interleaved.");
	}
	
	/**
	 * Checks if the file is valid, re-prompting the user for a valid file name
	 * if they entered an invalid one
	 * @param keyboard Scanner object
	 * @return name of the valid file
	 */
	public static String findFile(Scanner keyboard) {
		System.out.print("\nEnter secret file name: ");
		String file = keyboard.nextLine();
		boolean validFile = isValidFile(file);
		while(!validFile) {
			System.out.print("Enter secret file name: ");
			file = keyboard.nextLine();
			validFile = isValidFile(file);
		} 
		return file;
	}
	
	public static String findNextFile(Scanner keyboard) {
		System.out.print("Enter another secret file name (or blank): ");
		String file = keyboard.nextLine();
		if(file.isEmpty()) {
			return "";
		}
		boolean validFile = isValidFile(file);
		while(!validFile) {
			System.out.print("Enter another secret file name (or blank): ");
			file = keyboard.nextLine();
			validFile = isValidFile(file);
		} 
		return file;
	}
	
	/**
	 * Checks to see that the user-specified file name refers to a valid
	 * file on the disk and not a directory. Displays an error message to the
	 * user if that is not the case.
	 * @param fname file name string to check
	 * @return true if file exists on disk and is not a directory
	 */
	private static boolean isValidFile(String fname) {
	    File path = new File(fname);
	    boolean isValid = path.exists() && !path.isDirectory();
	    if (!isValid) {
	    	System.out.println("File name is invalid");
	    	return false;
	    }
	    return true;
	}

}
