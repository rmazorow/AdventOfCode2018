/*
Author: Rocky Mazorow
Date: 12/12/2018

The sleigh is much easier to pull than you'd expect for something its weight. Unfortunately, neither you nor the Elves know which way the North Pole is from here.

You check your wrist device for anything that might help. It seems to have some kind of navigation system! Activating the navigation system produces more bad news: "Failed to start navigation system. Could not read software license file."

The navigation system's license file consists of a list of numbers (your puzzle input). The numbers define a data structure which, when processed, produces some kind of tree that can be used to calculate the license number.

The tree is made up of nodes; a single, outermost node forms the tree's root, and it contains all other nodes in the tree (or contains nodes that contain nodes, and so on).

Specifically, a node consists of:

A header, which is always exactly two numbers:
The quantity of child nodes.
The quantity of metadata entries.
Zero or more child nodes (as specified in the header).
One or more metadata entries (as specified in the header).
Each child node is itself a node that has its own header, child nodes, and metadata. For example:

2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2
A----------------------------------
	B----------- C-----------
					 D-----
In this example, each node of the tree is also marked with an underline starting with a letter for easier identification. In it, there are four nodes:

A, which has 2 child nodes (B, C) and 3 metadata entries (1, 1, 2).
B, which has 0 child nodes and 3 metadata entries (10, 11, 12).
C, which has 1 child node (D) and 1 metadata entry (2).
D, which has 0 child nodes and 1 metadata entry (99).
The first check done on the license file is to simply add up all of the metadata entries. In this example, that sum is 1+1+2+10+11+12+2+99=138.

What is the sum of all metadata entries?
*/
import java.util.*;
import java.io.*;

class Day8_P1 {
	public static void main(String[] args) {
		try {
			Scanner input = new Scanner(new File("Day8.txt"));
			ArrayList<Integer> remain = new ArrayList<Integer>();
			Stack<Integer> children = new Stack<Integer>();
			//int kids = 0;
			int sum  = 0;
			
			// Read in first node and # of children nodes
			int node = input.nextInt();
			int meta = input.nextInt();
			children.push(node);
			
			node = input.nextInt();
			
			while(input.hasNextInt()) {
				meta = input.nextInt();
				
				//System.out.println(children);
				//System.out.println("Next 2 are: " + node + " " + meta);
			
				// If node has 0 children, then next x ints will be metadata
				if (!children.empty()) {
					int prev = children.pop() - 1;
					if (prev != 0) {
						children.push(prev);
					}
					
					if (node != 0) {
						children.push(node);
						//System.out.println("Node has " + node + " children");
					}
					else {
						for (int i = 0; i < meta; i++) {
							sum += input.nextInt();
						}
						//System.out.println(sum);
					}
				}
				if (children.empty()) {
					while(input.hasNextInt()) {
						sum += input.nextInt();
					}
				}
				
				// Read in next node if exists
				if (input.hasNextInt()) {
					node = input.nextInt();
				}
			}
			
			System.out.println("The sum of all metadata entries is: " + sum);
		} catch (IOException ex) {
			System.out.println("File not found");
		}
	}
}