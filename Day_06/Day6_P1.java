/*
Author: Rocky Mazorow
Date: 12/4/2018

--- Day 6: Chronal Coordinates ---
The device on your wrist beeps several times, and once again you feel like you're falling.

"Situation critical," the device announces. "Destination indeterminate. Chronal interference detected. Please specify new target coordinates."

The device then produces a list of coordinates (your puzzle input). Are they places it thinks are safe or dangerous? It recommends you check manual page 729. The Elves did not give you a manual.

If they're dangerous, maybe you can minimize the danger by finding the coordinate that gives the largest distance from the other points.

Using only the Manhattan distance, determine the area around each coordinate by counting the number of integer X,Y locations that are closest to that coordinate (and aren't tied in distance to any other coordinate).

Your goal is to find the size of the largest area that isn't infinite. For example, consider the following list of coordinates:

1, 1
1, 6
8, 3
3, 4
5, 5
8, 9
If we name these coordinates A through F, we can draw them on a grid, putting 0,0 at the top left:

..........
.A........
..........
........C.
...D......
.....E....
.B........
..........
..........
........F.
This view is partial - the actual grid extends infinitely in all directions. Using the Manhattan distance, each location's closest coordinate can be determined, shown here in lowercase:

aaaaa.cccc
aAaaa.cccc
aaaddecccc
aadddeccCc
..dDdeeccc
bb.deEeecc
bBb.eeee..
bbb.eeefff
bbb.eeffff
bbb.ffffFf
Locations shown as . are equally far from two or more coordinates, and so they don't count as being closest to any.

In this example, the areas of coordinates A, B, C, and F are infinite - while not shown here, their areas extend forever outside the visible grid. However, the areas of coordinates D and E are finite: D is closest to 9 locations, and E is closest to 17 (both including the coordinate's location itself). Therefore, in this example, the size of the largest area is 17.

What is the size of the largest area that isn't infinite?
 */
import java.util.*;
import java.io.*;
import java.awt.*;

public class Day6_P1 {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(new File("Day6.txt"));
            ArrayList<String> coord = new ArrayList<String>();
            ArrayList<String> valid = new ArrayList<String>();
            int topY = Integer.MAX_VALUE;
            int botY = Integer.MIN_VALUE;
            int lefX = Integer.MAX_VALUE;
            int rigX = Integer.MIN_VALUE;
            
            // Read in the coordinates and determine the height and width of grid
            while(input.hasNextLine()) {
                String temp = input.nextLine();
                int x = Integer.parseInt(temp.substring(0, temp.indexOf(",")));
                int y = Integer.parseInt(temp.substring(temp.indexOf(",") + 2));
                coord.add(temp);
                valid.add(temp);
                
                // If edge-most, set to new edge
                if (y < topY) {
                    topY = y;
                }
                else if (y > botY) {
                    botY = y;
                }
                if (x > rigX) {
                    rigX = x;
                }
                else if (x < lefX) {
                    lefX = x;
                }
            }
            // remove edges from valid list to prevent infinite areas
            for (String s : coord) {
                String x = s.substring(0, s.indexOf(","));
                String y = s.substring(s.indexOf(",") + 2);
                
                if (x.equals(lefX) || x.equals(rigX) || y.equals(topY) || y.equals(botY)) {
                    valid.remove(s);
                }
            }
            
            /*System.out.println("Top:   (" + topX + "," + topY + ")");
            System.out.println("Bot:   (" + botX + "," + botY + ")");
            System.out.println("Left:  (" + lefX + "," + lefY + ")");
            System.out.println("Right: (" + rigX + "," + rigY + ")");
            
            System.out.println("# coord: " + coord.size());
            System.out.println("width:   " + (rigX - lefX + 1));
            System.out.println("height:  " + (botY - topY + 1));*/
            
            HashMap<String, String> grid = new HashMap<String, String>();
            HashMap<String, Integer> dist = new HashMap<String, Integer>();
            
            // Build grid to contain coordinate and closet point (as index of coord)
            // Build dist to contain coordinate and distance to closet point
            for (int i = 0; i < coord.size(); i++) {
                String temp = coord.get(i);
                int x = Integer.parseInt(temp.substring(0, temp.indexOf(",")));
                int y = Integer.parseInt(temp.substring(temp.indexOf(",") + 2));
                
                for (int y2 = topY; y2 < botY + 1; y2++) {
                    for (int x2 = lefX; x2 < rigX + 1; x2++) {
                        String loc = x2 + ", " + y2;
                        int distance = Math.abs(x - x2) + Math.abs(y - y2);
                        if (!grid.containsKey(loc)) {
                            dist.put(loc, distance);
                            grid.put(loc, Integer.toString(i));
                        }
                        else {
                            if (distance < dist.get(loc)) {
                                dist.replace(loc, distance);
                                grid.replace(loc, Integer.toString(i));
                            }
                            else if(distance == dist.get(loc)) {
                                grid.replace(loc, ".");
                            }
                        }
                    }
                }
            }
            /*for (int y3 = topY; y3 < botY + 1; y3++) {
                for (int x3 = lefX; x3 < rigX + 1; x3++) {
                    System.out.print(grid.get(x3 + ", " + y3) + " ");
                }
                System.out.println();
            }
            System.out.println();*/
            
            int max = 0;
            
            for (int i = 0; i < coord.size(); i++) {
                int count = 0;
                
                if (valid.contains(coord.get(i))){
                    for (String c : grid.values()) {
                        if (Integer.toString(i).equals(c)) {
                            count++;
                        }
                    }
                }
                
                //System.out.println(coord.get(i) + "(" + i + "): " + count);
                if (count > max) {
                    max = count;
                }
            }
            
            System.out.println("The size of the largest area that isn't infinite is: " + max);
        }
        catch(IOException ex) {
            System.out.println("File not found");
        }
    }
}
