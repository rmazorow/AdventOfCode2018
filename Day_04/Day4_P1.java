/*
Author: Rocky Mazorow
Date: 12/4/2018

You've sneaked into another supply closet - this time, it's across from the prototype suit manufacturing lab. You need to sneak inside and fix the issues with the suit, but there's a guard stationed outside the lab, so this is as close as you can safely get.

As you search the closet for anything that might help, you discover that you're not the first person to want to sneak in. Covering the walls, someone has spent an hour starting every midnight for the past few months secretly observing this guard post! They've been writing down the ID of the one guard on duty that night - the Elves seem to have decided that one guard was enough for the overnight shift - as well as when they fall asleep or wake up while at their post (your puzzle input).

For example, consider the following records, which have already been organized into chronological order:
	[1518-11-01 00:00] Guard #10 begins shift
	[1518-11-01 00:05] falls asleep
	[1518-11-01 00:25] wakes up
	[1518-11-01 00:30] falls asleep
	[1518-11-01 00:55] wakes up
	[1518-11-01 23:58] Guard #99 begins shift
	[1518-11-02 00:40] falls asleep
	[1518-11-02 00:50] wakes up
	[1518-11-03 00:05] Guard #10 begins shift
	[1518-11-03 00:24] falls asleep
	[1518-11-03 00:29] wakes up
	[1518-11-04 00:02] Guard #99 begins shift
	[1518-11-04 00:36] falls asleep
	[1518-11-04 00:46] wakes up
	[1518-11-05 00:03] Guard #99 begins shift
	[1518-11-05 00:45] falls asleep
	[1518-11-05 00:55] wakes up
	
Timestamps are written using year-month-day hour:minute format. The guard falling asleep or waking up is always the one whose shift most recently started. Because all asleep/awake times are during the midnight hour (00:00 - 00:59), only the minute portion (00 - 59) is relevant for those events.

Visually, these records show that the guards are asleep at these times:

Date   ID   Minute
		000000000011111111112222222222333333333344444444445555555555
		012345678901234567890123456789012345678901234567890123456789
11-01  #10  .....####################.....#########################.....
11-02  #99  ........................................##########..........
11-03  #10  ........................#####...............................
11-04  #99  ....................................##########..............
11-05  #99  .............................................##########.....
The columns are Date, which shows the month-day portion of the relevant day; ID, which shows the guard on duty that day; and Minute, which shows the minutes during which the guard was asleep within the midnight hour. (The Minute column's header shows the minute's ten's digit in the first row and the one's digit in the second row.) Awake is shown as ., and asleep is shown as #.

Note that guards count as asleep on the minute they fall asleep, and they count as awake on the minute they wake up. For example, because Guard #10 wakes up at 00:25 on 1518-11-01, minute 25 is marked as awake.

If you can figure out the guard most likely to be asleep at a specific time, you might be able to trick that guard into working tonight so you can have the best chance of sneaking in. You have two strategies for choosing the best guard/minute combination.

Strategy 1: Find the guard that has the most minutes asleep. What minute does that guard spend asleep the most?

In the example above, Guard #10 spent the most minutes asleep, a total of 50 minutes (20+25+5), while Guard #99 only slept for a total of 30 minutes (10+10+10). Guard #10 was asleep most during minute 24 (on two days, whereas any other minute the guard was asleep was only seen on one day).

While this example listed the entries in chronological order, your entries are in the order you found them. You'll need to organize them before they can be analyzed.

What is the ID of the guard you chose multiplied by the minute you chose? (In the above example, the answer would be 10 * 24 = 240.)
*/
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.*;

class Day4_P1 {
	public static int max(int[] temp) {
		int max = temp[0];
		int mId = 0;
		
		for (int i = 1; i < temp.length; i++) {
			if (temp[i] > max) {
				max = temp[i];
				mId = i;
			}
		}
		
		return mId;
	}
	
	public static void main(String[] args) {
		try {
			Scanner input  = new Scanner(new File("Day4_Sorted.txt"));
			HashMap<Integer, ArrayList<Object>> schedule = new HashMap<>();
			int[] minutes = new int[60];
			String time   = "";
			boolean awake = false;
			int id  = 0;
			int sum = 0;
			int max = 0;
			int mId = 0;
			int start = 0;
			int end   = 0;
			
			while(input.hasNext()) {
				time = input.nextLine();
				int indCol = time.indexOf(":");
				
				if (time.contains("Guard")) {
					int indPd = time.indexOf("#");
					id = Integer.parseInt(time.substring(indPd + 1, time.indexOf(" ", indPd)));
					awake = false;
				}
				else if (time.contains("wakes")) {
					end   = Integer.parseInt(time.substring(indCol + 1, indCol + 3));
					awake = true;
				}
				else if (time.contains("falls")) {
					start = Integer.parseInt(time.substring(indCol + 1, indCol + 3));
					awake = false;
				}
				
				if (awake) {
					int s = 0;
					if (schedule.containsKey(id)) {
						ArrayList<Object> guard = schedule.get(id);
						s	  = (int)(guard.get(0));
						int[] min = (int[])guard.get(1);
						
						s += (end - start) % 60;
						guard.set(0, s);
						
						for (int i = start; i < end; i++) {
							min[i]++;
						}
						guard.set(1, min);
						
						schedule.replace(id, guard);
					}
					else {
						ArrayList<Object> guard = new ArrayList<Object>();
						int[] min = new int[60];
						
						s += (end - start) % 60;
						guard.add(s);
						
						for (int i = start; i < end; i++) {
							min[i]++;
						}
						guard.add(min);
						
						schedule.put(id, guard);
					}
					
					if (s > max) {
						max = s;
						mId = id;
					}
				}
			}
			input.close();
			
			ArrayList<Object> guard = schedule.get(mId);
			int[] min = (int[])guard.get(1);
			int maxMin = max(min);
			
			System.out.println("ID of the guard chosen: " + 
						 mId + "\n       * minute chosen: " +
						 maxMin + "\n------------------------------\n\t\t Total: " + (maxMin * mId));
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
}