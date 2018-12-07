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
import java.util.*;
import java.io.*;

class Day3_P2 {
	public static void main(String[] args) {
		try {
			Scanner input   = new Scanner(new File("Day3.txt"));
			HashMap<String, String> fabric = new HashMap<String, String>();
			ArrayList<String> ids   = new ArrayList<String>();
			boolean overlaps = false;
			String claim = "";
			String id    = "";
			int indPd  = 0;
			int indSp  = 0;
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
				indPd  = claim.indexOf('#');
				indSp  = claim.indexOf(' ');
				indAt  = claim.indexOf('@');
				indCom = claim.indexOf(',');
				indCol = claim.indexOf(':');
				indX   = claim.indexOf('x');
				id     = claim.substring(indPd + 1, indSp);
				left   = Integer.parseInt(claim.substring(indAt + 2, indCom));
				top    = Integer.parseInt(claim.substring(indCom + 1, indCol));
				width  = Integer.parseInt(claim.substring(indCol + 2, indX));
				height = Integer.parseInt(claim.substring(indX + 1));
				
				// Create coordinates for claimed fabric
				for (int w = left; w < left + width; w++) {
					for (int h = top; h < top + height; h++) {
						String coord = w + "," + h;
						
						// HashMap is <String coordinate, String idList>
						// Insert claim id if it includes the coordinate
						if(!fabric.containsKey(coord)) {
							fabric.put(coord, " " + id);
						}
						else {
							fabric.replace(coord, fabric.get(coord) + " " + id);
							overlaps = true;
						}
					}
				}
				// If nothing currently overlaps with this id claim,
				// add to ArrayList of ids
				if (!overlaps) {
					ids.add(id);
				}
				overlaps = false;
			}
			input.close();
			
			String unique = "";
			
			// Check all non-overlapping ids at time of inspection
			// and find the one that still does not overlap
			for (int i = 0; i < ids.size(); i++) {
				// Declare iterator for HashMap fabric
				Iterator entries = fabric.entrySet().iterator();
				while (entries.hasNext() && !overlaps) {
					Map.Entry entry = (Map.Entry) entries.next();
					String swatch = (String)entry.getValue();
					
					// If HashMap value contains id, see how many ids that coord contains
					// If > 1 id, then this claim has an overlap and we can skip
					if (swatch.contains(" " + ids.get(i))) {
						String[] temp = swatch.split(" ");
						
						if (temp.length > 2) {
							overlaps = true;
							break;
						}
					}
				}
				if (!overlaps) {
					unique = ids.get(i);
					break;
				}
				overlaps = false;
			}

			
			System.out.println("The ID of the only claim that doesn't overlap is " + unique);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}