/*
Author: Rocky Mazorow
Date: 12/4/2018

Takes an unsorted file with each row starting with format [YYY-MM-DD hh:mm], sorts it by date/hour, and exports it to a new file
*/
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.*;

class Day4_SortTxt {
	
	public static void main(String[] args) {
		try {
			Scanner input  = new Scanner(new File("Day4.txt"));
			ArrayList<String> file = new ArrayList<String>();
			
			while(input.hasNext()) {
				String line = input.nextLine();
				file.add(line);
			}
			input.close();
			
			Collections.sort(file);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("Day4_Sorted.txt"));
			String newLine = "";
			
			for (int i = 0; i < file.size() - 1; i++) {
				newLine = file.get(i) + "\n";
				writer.write(newLine);
			}
			newLine = file.get(file.size() - 1);
			writer.write(newLine);
			writer.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		catch(IOException e) {
			System.out.println("Could not write to file");
		}
	}
}