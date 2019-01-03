/*
Author: Rocky Mazorow
Date: 12/2/2018

You find yourself standing on a snow-covered coastline; apparently, you landed a little off course. The region is too hilly to see the North Pole from here, but you do spot some Elves that seem to be trying to unpack something that washed ashore. It's quite cold out, so you decide to risk creating a paradox by asking them for directions.

"Oh, are you the search party?" Somehow, you can understand whatever Elves from the year 1018 speak; you assume it's Ancient Nordic Elvish. Could the device on your wrist also be a translator? "Those clothes don't look very warm; take this." They hand you a heavy coat.

"We do need to find our way back to the North Pole, but we have higher priorities at the moment. You see, believe it or not, this box contains something that will solve all of Santa's transportation problems - at least, that's what it looks like from the pictures in the instructions." It doesn't seem like they can read whatever language it's in, but you can: "Sleigh kit. Some assembly required."

"'Sleigh'? What a wonderful name! You must help us assemble this 'sleigh' at once!" They start excitedly pulling more parts out of the box.

The instructions specify a series of steps and requirements about which steps must be finished before others can begin (your puzzle input). Each step is designated by a single letter. For example, suppose you have the following instructions:

Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.
Visually, these requirements look like this:


	-->A--->B--
 /    \      \
C      -->D----->E
 \           /
	---->F-----
	
Your first goal is to determine the order in which the steps should be completed. If more than one step is ready, choose the step which is first alphabetically. In this example, the steps would be completed as follows:

Only C is available, and so it is done first.
Next, both A and F are available. A is first alphabetically, so it is done next.
Then, even though F was available earlier, steps B and D are now also available, and B is the first alphabetically of the three.
After that, only D and F are available. E is not available because only some of its prerequisites are complete. Therefore, D is completed next.
F is the only choice, so it is done next.
Finally, E is completed.
So, in this example, the correct order is CABDFE.

In what order should the steps in your instructions be completed?
*/
import java.util.*;
import java.io.*;
import java.util.*;

class Day7_P1 {
	private static ArrayList<Character> avail = new ArrayList<Character>();
	
	// Recursively go through the map and build the step string 
	public static String next(HashMap<Character, ArrayList<Character>> map, int size, String s) {
		if (s.length() == size + 4) {
			return s;
		}
		else {
			boolean ready = true;
			
			// Find the steps whose prerequisites have been met 
			for (char next : map.keySet()) {
				ArrayList<Character> temp = map.get(next);			
				for (char c : temp) {
					if (!s.contains(Character.toString(c))) {
						ready = false;
					}
				}
				
				// If prerequisite met, add to available list
				if (ready && !avail.contains(next) &&!s.contains(Character.toString(next))) {
					avail.add(next);
				}
				ready = true;
			}
			
			// Sort available alphabetically and choose the first step to iterate through next
			Collections.sort(avail);
			char next = avail.get(0);
			while(avail.contains(next)) {
				avail.remove((Character)next);
			}
			map.remove(next);
			
			s += next;
			return next(map, size, s);
		}
	}
	
	public static void main(String[] args) {
		try {
			Scanner input = new Scanner(new File("Day7.txt"));
			HashMap<Character, ArrayList<Character>> map = new HashMap<Character, ArrayList<Character>>();
			String instr = "";
			String steps = "";
			
			while (input.hasNextLine()) {
				String temp = input.nextLine();
				char after = temp.charAt(5);
				char key   = temp.charAt(temp.indexOf("step") + 5);
				
				if (!map.containsKey(key)) {
					ArrayList<Character> pre = new ArrayList<Character>();
					pre.add(after);
					map.put(key, pre);
				}
				else {
					ArrayList<Character> pre = map.get(key);
					pre.add(after);
					Collections.sort(pre);
					map.put(key, pre);
				}
				if (!steps.contains(Character.toString(after))) {
					steps += after + " ";
				}
			}
			
			// Determine first step
			int fInd = 0;
			for (int i = 0; i < steps.length(); i += 2) {
				char test = steps.charAt(i);
				if (!map.containsKey(test)) {
					for (ArrayList<Character> temp : map.values()) {
						if (!avail.contains(test) && temp.contains(test)) {
							avail.add(test);
						}
					}
				}
			}
			
			Collections.sort(avail);
			instr += avail.get(0);
			avail.remove(0);
			instr = next(map, map.size(), instr);
			System.out.println("Steps should be taken in the following order: " + instr);
		}
		catch (IOException ex) {
			System.out.println("File not found");
		}	
	}
}