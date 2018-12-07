/*
Author: Rocky Mazorow
Date: 12/4/2018

You've managed to sneak in to the prototype suit manufacturing lab. The Elves are making decent progress, but are still struggling with the suit's size reduction capabilities.

While the very latest in 1518 alchemical technology might have solved their problem eventually, you can do better. You scan the chemical composition of the suit's material and discover that it is formed by extremely long polymers (one of which is available as your puzzle input).

The polymer is formed by smaller units which, when triggered, react with each other such that two adjacent units of the same type and opposite polarity are destroyed. Units' types are represented by letters; units' polarity is represented by capitalization. For instance, r and R are units with the same type but opposite polarity, whereas r and s are entirely different types and do not react.

For example:

In aA, a and A react, leaving nothing behind.
In abBA, bB destroys itself, leaving aA. As above, this then destroys itself, leaving nothing.
In abAB, no two adjacent units are of the same type, and so nothing happens.
In aabAAB, even though aa and AA are of the same type, their polarities match, and so nothing happens.
Now, consider a larger example, dabAcCaCBAcCcaDA:

dabAcCaCBAcCcaDA  The first 'cC' is removed.
dabAaCBAcCcaDA    This creates 'Aa', which is removed.
dabCBAcCcaDA      Either 'cC' or 'Cc' are removed (the result is the same).
dabCBAcaDA        No further actions can be taken.
After all possible reactions, the resulting polymer contains 10 units.

How many units remain after fully reacting the polymer you scanned?
 */
import java.util.Scanner;
import java.io.File;

public class Day5_P1 {
    public static String removeReactions(String s) {
        int index = -1;
        
        for (int i = 0; i < s.length() - 1; i++) {
            // Check that one letter is upper and the other is lower
            // If equal store the first index
            if ((Character.isUpperCase(s.charAt(i)) || Character.isUpperCase(s.charAt(i+1))) &&
                    (Character.isLowerCase(s.charAt(i)) || Character.isLowerCase(s.charAt(i+1))) &&
                    (Character.toLowerCase(s.charAt(i)) == Character.toLowerCase(s.charAt(i+1)))) {
                index = i;
                break;
            }
        }
        
        // If index = -1, then there are no reactions and recursion is done
        if (index == -1) {
            return s;
        }
        // If index != -1, then there are reactions, so remove reactions and re-enter method
        else {
            String sub = s.substring(index, index + 2);
            s = s.replace(sub, "");
            s = removeReactions(s);
            return s;
        }
    }
    
    public static void main(String[] args) {
        try {
            Scanner input  = new Scanner(new File("Day5.txt"));
            String polymer = input.next();
        
            String reacted = removeReactions(polymer);
            //System.out.println(reacted);
            System.out.println("There are " + reacted.length() + " units remaining after fully reacting the polymer");
            
        }
        catch (Exception ex) {
            System.out.println("File not found");
        }
    }
}
