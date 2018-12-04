/*
Author: Rocky Mazorow
Date: 12/3/2018

Amidst the chaos, you notice that exactly one claim doesn't overlap by even a single square inch of fabric with any other claim. If you can somehow draw attention to it, maybe the Elves will be able to make Santa's suit after all!

For example, in the claims below, only claim 3 is intact after all claims are made.
	#1 @ 1,3: 4x4
	#2 @ 3,1: 4x4
	#3 @ 5,5: 2x2
	Visually, these claim the following areas:

	........
	...2222.
	...2222.
	.11XX22.
	.11XX22.
	.111133.
	.111133.
	........

What is the ID of the only claim that doesn't overlap?
*/
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.*;

class Day3_P2 {
	static ArrayList<ArrayList<Integer>> fabric = new ArrayList<ArrayList<Integer>>();
	
	public static void addNew(int w, int h) {
		try {
			ArrayList<Integer> temp = fabric.get(w);
			temp.add(h, 1);
		} catch (Exception e) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(h, 1);
			fabric.add(w, temp);
		}
	}
	
	public static void main(String[] args) {
		try {
			Scanner input   = new Scanner(new File("Day3.txt"));
			boolean overlap = false;
			String claim    = "";
			String id       = "";
			int indAt  = 0;
			int indCom = 0;
			int indCol = 0;
			int indX   = 0;
			int left   = 0;
			int top    = 0;
			int width  = 0;
			int height = 0;
			
			while(input.hasNext()) {
				claim = input.nextLine();
				// #id @ left,top: widthxheight
				indAt  = claim.indexOf('@');
				indCom = claim.indexOf(',');
				indCol = claim.indexOf(':');
				indX   = claim.indexOf('x');
				left   = Integer.parseInt(claim.substring(indAt + 2, indCom));
				top    = Integer.parseInt(claim.substring(indCom + 1, indCol));
				width  = Integer.parseInt(claim.substring(indCol + 2, indX));
				height = Integer.parseInt(claim.substring(indX + 1));
				
				// Create coordinates for claimed fabric
				for (int w = left; w < left + width; w++) {
					for (int h = top; h < top + height; h++) {
						try {
							ArrayList<Integer> temp = fabric.get(w);
							int current = temp.get(h);
							temp.set(h, current + 1);
						}
						catch (Exception e) {
							addNew(w, h);
						}
					}
				}
			}
			input.close();
			
			System.out.println("The ID of the only claim that doesn't overlap is " + id);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}