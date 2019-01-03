/*
Author: Rocky Mazorow
Date: 12/2/2018

You notice that the device repeats the same frequency change list over and over. To calibrate the device, you need to find the first frequency it reaches twice.

For example, using the same list of changes above, the device would loop as follows:

Current frequency  0, change of +1; resulting frequency  1.
Current frequency  1, change of -2; resulting frequency -1.
Current frequency -1, change of +3; resulting frequency  2.
Current frequency  2, change of +1; resulting frequency  3.
(At this point, the device continues from the start of the list.)
Current frequency  3, change of +1; resulting frequency  4.
Current frequency  4, change of -2; resulting frequency  2, which has already been seen.
In this example, the first frequency reached twice is 2. Note that your device might need to repeat its list of frequency changes many times before a duplicate frequency is found, and that duplicates might be found while in the middle of processing the list.

Here are other examples:

+1, -1 first reaches 0 twice.
+3, +3, +4, -2, -4 first reaches 10 twice.
-6, +3, +8, +5, -6 first reaches 5 twice.
+7, +7, -2, -7, -4 first reaches 14 twice.
What is the first frequency your device reaches twice?
*/
import java.util.Scanner;
import java.io.File;
import java.io.*;

class Day1_P2 {
	public static void main(String[] args) {
		try {
			Scanner input;
			boolean found = false;
			String freq   = "0 ";
			String temp   = "";
			int total = 0;
			
			while(!found) {
				input = new Scanner(new File("Day1.txt"));
				
				while(input.hasNext()) {
					temp = input.next();
					
					if (temp.charAt(0) == '+') {
						total += Integer.parseInt(temp.substring(1));
					}
					else {
						total -= Integer.parseInt(temp.substring(1));
					}
					
					if (!freq.contains(" " + Integer.toString(total) + " ")) {
						freq += total + " ";
					}
					else {
						found = true;
						break;
					}
				}
				input.close();
			}
			System.out.println("Resulting frequency is: " + total);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}