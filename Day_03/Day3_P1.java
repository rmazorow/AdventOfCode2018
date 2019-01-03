/*
Author: Rocky Mazorow
Date: 12/3/2018

The Elves managed to locate the chimney-squeeze prototype fabric for Santa's suit (thanks to someone who helpfully wrote its box IDs on the wall of the warehouse in the middle of the night). Unfortunately, anomalies are still affecting them - nobody can even agree on how to cut the fabric.

The whole piece of fabric they're working on is a very large square - at least 1000 inches on each side.

Each Elf has made a claim about which area of fabric would be ideal for Santa's suit. All claims have an ID and consist of a single rectangle with edges parallel to the edges of the fabric. Each claim's rectangle is defined as follows:

The number of inches between the left edge of the fabric and the left edge of the rectangle.
The number of inches between the top edge of the fabric and the top edge of the rectangle.
The width of the rectangle in inches.
The height of the rectangle in inches.
A claim like #123 @ 3,2: 5x4 means that claim ID 123 specifies a rectangle 3 inches from the left edge, 2 inches from the top edge, 5 inches wide, and 4 inches tall. Visually, it claims the square inches of fabric represented by # (and ignores the square inches of fabric represented by .) in the diagram below:

...........
...........
...#####...
...#####...
...#####...
...#####...
...........
...........
...........
The problem is that many of the claims overlap, causing two or more claims to cover part of the same areas. For example, consider the following claims:

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
The four square inches marked with X are claimed by both 1 and 2. (Claim 3, while adjacent to the others, does not overlap either of them.)

If the Elves all proceed with their own plans, none of them will have enough fabric. How many square inches of fabric are within two or more claims?
*/
import java.util.Scanner;
import java.io.File;
import java.io.*;

class Day3_P1 {
	public static void main(String[] args) {
		try {
			Scanner input  = new Scanner(new File("Day3.txt"));
			String claim   = "";
			String fabric  = "";
			String overlap = "";
			int overCount  = 0;
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
						String test = w + "," + h + " ";
						if (!fabric.contains(" " + test)) {
							fabric += test;
						}
						else if (!overlap.contains(" " + test)) {
							overlap += test;
						}
					}
				}
			}
			input.close();
			
			// Count overlap areas
			String[] count = overlap.split(" ");
			overCount = count.length;
			
			System.out.println("There are " + overCount + " square inches of fabric within two or more claims");
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}