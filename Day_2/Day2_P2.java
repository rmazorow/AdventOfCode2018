/*
Author: Rocky Mazorow
Date: 12/2/2018

Confident that your list of box IDs is complete, you're ready to find the boxes full of prototype fabric.

The boxes will have IDs which differ by exactly one character at the same position in both strings. For example, given the following box IDs:

abcde
fghij
klmno
pqrst
fguij
axcye
wvxyz
The IDs abcde and axcye are close, but they differ by two characters (the second and fourth). However, the IDs fghij and fguij differ by exactly one character, the third (h and u). Those must be the correct boxes.

What letters are common between the two correct box IDs? (In the example above, this is found by removing the differing character from either ID, producing fgij.)
*/
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.*;
import java.awt.print.*;

class Day2_P1 {
	public static void main(String[] args) {
		try {
			Scanner input = new Scanner(new File("Day2.txt"));
			ArrayList<String> list = new ArrayList<String>();
			boolean found = false;
			String id = "";
			
			while(input.hasNext()) {
				list.add(input.next());
			}
			input.close();
			
			// for each word
			for(int i = 0; i < list.size() - 1; i++) {
				// compare to each word after word i
				for (int j = i + 1; j < list.size() && !found; j++) {
					// counter for non-matching characters
					int noMatch = 0;
					// check each character
					for (int c = 0; c < list.get(i).length(); c++) {
						// if not matching char, add to counter
						if (list.get(i).charAt(c) != list.get(j).charAt(c)) {
							noMatch++;
						}
						// check to make sure not greater than 1 non-matching char
						if (noMatch > 1) {
							break;
						}
						
						// remove non-matching char and compare new strings
						String tempI = list.get(i).substring(0, c) + list.get(i).substring(c+1);
						String tempJ = list.get(j).substring(0, c) + list.get(j).substring(c+1);
						if(tempI.equals(tempJ)) {
							found = true;
							id = tempI;
							break;
						}
					}
				}
			}
			
			System.out.println("The letters are common between the two correct box IDs are : " + id);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}