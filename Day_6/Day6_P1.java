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

public class Day6_P1 {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(new File("Day6_Test.txt"));
            ArrayList<String> coord = new ArrayList<String>();
            int topX = Integer.MAX_VALUE;
            int topY = Integer.MAX_VALUE;
            int botX = Integer.MIN_VALUE;
            int botY = Integer.MIN_VALUE;
            int lefX = Integer.MAX_VALUE;
            int lefY = Integer.MAX_VALUE;
            int rigX = Integer.MIN_VALUE;
            int rigY = Integer.MIN_VALUE;
            
            while(input.hasNextLine()) {
                String temp = input.nextLine();
                int x = Integer.parseInt(temp.substring(0, temp.indexOf(",")));
                int y = Integer.parseInt(temp.substring(temp.indexOf(",") + 2));
                coord.add(temp);
                
                if (y < topY) {
                    topX = x;
                    topY = y;
                    coord.remove(temp);
                }
                else if (y > botY) {
                    botX = x;
                    botY = y;
                    coord.remove(temp);
                }
                if (x > rigX) {
                    rigX = x;
                    rigY = y;
                    coord.remove(temp);
                }
                else if (x < lefX) {
                    lefX = x;
                    lefY = y;
                    coord.remove(temp);
                }
            }
            
            /*
            System.out.println("Top:   (" + topX + "," + topY + ")");
            System.out.println("Bot:   (" + botX + "," + botY + ")");
            System.out.println("Left:  (" + lefX + "," + lefY + ")");
            System.out.println("Right: (" + rigX + "," + rigY + ")");
            */
            
            String[][] grid = new String[rigX - lefX + 1][botY - topY + 1];
            int[][] dist = new int[rigX - lefX + 1][botY - topY + 1];
            
            for (int i = 0; i < coord.size(); i++) {
                String temp = coord.get(i);
                int x = Integer.parseInt(temp.substring(0, temp.indexOf(",")));
                int y = Integer.parseInt(temp.substring(temp.indexOf(",") + 2));
                for (int row = 0; row <= botY - topY + 1; row++) {
                    for (int col = 0; col <= rigY - lefY + 1; col++) {
                        int distance = Math.abs(x - col) + Math.abs(y - row);
                        if (distance < dist[row][col]) {
                            dist[row][col] = distance;
                            grid[row][col] = Integer.toString(i);
                        }
                        else if(distance == dist[row][col]) {
                            grid[row][col] = ".";
                        }
                    }
                }
            }
            
            for (int row = 0; row <= botY - topY + 1; row++) {
                for (int col = 0; col <= rigY - lefY + 1; col++) {
                    System.out.print(grid[row][col]);
                }
                System.out.println();
            }
            
        }
        catch(IOException ex) {
            System.out.println("File not found");
        }
    }
}
