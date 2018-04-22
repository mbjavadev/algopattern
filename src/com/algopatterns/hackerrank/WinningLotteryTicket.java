package com.algopatterns.hackerrank;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/*

Reference https://www.hackerrank.com/contests/hackerrank-hiring-contest/challenges/winning-lottery-ticket
 
The SuperBowl Lottery is about to commence, and there are several lottery tickets being sold, and each ticket is identified with a ticket ID. In one of the many winning scenarios in the Superbowl lottery, a winning pair of tickets is:

Concatenation of the two ticket IDs in the pair, in any order, contains each digit from  0 to 9 at least once.

For example, if there are  distinct tickets with ticket ID  129300455 and , 56789 (129300455, 56789) is a winning pair.

NOTE: The ticket IDs can be concantenated in any order. Digits in the ticket ID can occur in any order.

Your task is to find the number of winning pairs of distinct tickets, such that concatenation of their ticket IDs (in any order) makes for a winning scenario. Complete the function winningLotteryTicket which takes a string array of ticket IDs as input, and return the number of winning pairs.

Input Format

The first line contains  denoting the total number of lottery tickets in the super bowl. 
Each of the next  lines contains a string, where string on a  line denotes the ticket id of the  ticket.

Constraints

1 <= n <= 10^6
1 <= length of ticket_i<=10^6
sum of lengths of all tickets_i<= 10^6
Each ticket id consists of digits from [0,9]

Output Format

Print the number of pairs in a new line

Sample Input 0

5
129300455 
5559948277
012334556 
56789
123456879 

Sample Output 0

5

Notice that each winning pair has digits from  to  atleast once, and the digits in the ticket ID can be of any order. Thus, the number of winning pairs is 5.

Example.

Input
-----
11
0123456789
1023456789
1023456789
129300455
12930045
129304550
5559948277
012334556
56789
5678955
0123456879

output
------
43

// For Above Example Bitmask Array with count would be
 bitMask[31]   = 2
 bitMask[183]  = 1
 bitMask[1009] = 3
 bitMask[1016] = 1
 bitMask[1023] = 4 

 So Let's say for first iteration. We are skipping indexes with values zero 
 i = 31, j = 183
 Count = 0;
 Do or operation with each next elements
  31 | 183 = 191 (!= 1023)
  31 | 1009 = 1023   count = count + 3  (count of 1009)
  31 | 1016 = 1023   count = count + 1  (count of 1016)
  31 | 1023 = 1023   count = count + 4  (count of 1023)
  
  Count = 8 for 31. 
  Now 31 it self has 2 count so 8 * 2 = 16; 
  
  Similar way other counts
  
  Bit Mask Values			  31  183  1009 1016  1023  
			
  After Iteration			  16   5    12    4    6 (See below)      = 43
  
  For 1023 it self is valid Lottery ID so we will have to follow similar count pattern 
   
  Here 1023 has four count 
  
  1023 1023 1023 1023
  3     2    1        = 6 count
  
*/
public class WinningLotteryTicket {

	/**
	 * Comparing 1024 * 1024 values and marking count.
	 * @param bitMask
	 * @return
	 */
	static long winningLotteryTicket(long[] bitMask) {
    	long count = 0;
        for(int i = 0 ; i < bitMask.length - 1; i++) {
        	if(bitMask[i] > 0) {
	            for(int j = i+1 ; j < bitMask.length;j++){
	            	if(bitMask[j] > 0) {
		            	if((i | j) == 1023)
		            	{
		            		count = count + (bitMask[j] * bitMask[i]);   
		            	}
	            	}
	            }
        	}
        }
        
        if(bitMask[1023] > 0) {
        	long n = bitMask[1023] - 1;   // (n * (n +1))/2  for last 1023 as it's self contain valid key
        	count += (n * (n+1))/2;
        }
        return count;
    }
    
    
    static String removeDuplicatesAndMakeTotal(String ticket) {
    	Set<Character> charSet = new HashSet<Character>();
    	for (char c: ticket.toCharArray()) {
            charSet.add(c);
    	}
    	
    	Iterator<Character> it = charSet.iterator();
    	StringBuilder ticketStr = new StringBuilder("");
    	while(it.hasNext()) {
    		ticketStr.append(it.next());
    	}
    	return ticketStr.toString();
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] bitMask = new int[n];
        
        //Created BitMask Array for each ticket.
        //Max bitmask value would be 1023.
        for(int tickets_i = 0; tickets_i < n; tickets_i++){
        	bitMask[tickets_i] = getBitMask(in.next());
        }
        
        //Max Bit mask value would be 1023 so we will create array of size 1024 and mark count of bitmask values occur in array.
        long[] finalArray = new long[1024];
        for(int i = 0; i < bitMask.length; i++) {
        	long count = finalArray[bitMask[i]];
        	count++;
        	finalArray[bitMask[i]] = count;
        }
        long result = winningLotteryTicket(finalArray);
        System.out.println(result);
        in.close();
    }
    
    /**
     * Bit Masking the Ticket ID. For example 0123456789 -> 1111111111 so value would be 1023. Now if we do or operation with 
     * any Bit masking value of other ticket then it would be 1023.
     * @param str
     * @return
     */
    static int getBitMask(String str) {
    	char[] bitStr = new char[] {'0','0','0','0','0','0','0','0','0','0'};
    	for(char c : str.toCharArray()) {
    		int val = Integer.parseInt(""+c);
    		bitStr[val] = '1';
    	}
    	String newString = new String(bitStr);
    	return Integer.parseInt(newString, 2);
    }
}
