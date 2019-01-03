/*
/*
Author: Rocky Mazorow
Date: 12/4/2018

Time to improve the polymer.

One of the unit types is causing problems; it's preventing the polymer from collapsing as much as it should. Your goal is to figure out which unit type is causing the most problems, remove all instances of it (regardless of polarity), fully react the remaining polymer, and measure its length.

For example, again using the polymer dabAcCaCBAcCcaDA from above:

Removing all A/a units produces dbcCCBcCcD. Fully reacting this polymer produces dbCBcD, which has length 6.
Removing all B/b units produces daAcCaCAcCcaDA. Fully reacting this polymer produces daCAcaDA, which has length 8.
Removing all C/c units produces dabAaBAaDA. Fully reacting this polymer produces daDA, which has length 4.
Removing all D/d units produces abAcCaCBAcCcaA. Fully reacting this polymer produces abCBAc, which has length 6.
In this example, removing all C/c units was best, producing the answer 4.

What is the length of the shortest polymer you can produce by removing all units of exactly one type and fully reacting the result?
 */
import java.util.Scanner;
import java.io.*;

public class Day5_P2 {
    static String[] alph   = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    static int[] reactions = new int[26];
    
    public static int minArray(int[] r) {
        int min = r[0];
        
        for (int i = 1; i < r.length; i++) {
            if (r[i] < min && r[i] != 0) {
                min = r[i];
            }
        }
        
        return  min;
    }
    
    // Keeps track of original polymer for each A/a - Z/z unit removal
    public static void findShortestPolymer(String s) {
        int alphInd = 0;
        while (alphInd < 26) {
            removeReactions(s, alphInd);
            alphInd++;
        }
    }
    
    // Removes A/a - Z/z units then sends to remove reactions
    public static void removeReactions(String s, int alphInd) {
        s = s.replaceAll(alph[alphInd], "");
        s = s.replaceAll(alph[alphInd].toUpperCase(), "");
        
        s = removeReactions(s);
        reactions[alphInd] = s.length();
    }
    
    // Removes upper/lowercase polymer reactions
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
        
            findShortestPolymer(polymer);
            //System.out.println(reacted);
            System.out.println("There are " + minArray(reactions) + " units remaining after fully reacting the polymer");
        }
        catch (IOException ex) {
            System.out.println("File not found");
        }
    }
}
