/**
 * 
 */
package com.chris.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjmcmill
 * 
 */
public class Convert
{
    static final String[] DIGITS =
	    { "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
		    "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
		    "eighteen", "nineteen" };
    /**
     * 
     * Note: 10-19 are "special" and use digits
     */
    static final String[] TENS_DIGITS =
	    { "", "", "twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety" };

    static final String[] SCALE_DIGITS =
	    { "", "hundred", "thousand", "million", "billion", "trillion", "quadrillion",
		    "quintillion", "sextillion", "septillion", "octillion", "nonillion" };

    /**
     * Convert an int to its numeral name equivalent
     * @param n Number
     * @return numeral name as String
     */
    public static String getNumeralName(int n)
    {
	StringBuilder digit = new StringBuilder();
	if (n < 0)
	{
	    // Mark negative numbers and switch the value of n
	    digit.append("negative");
	    n = Math.abs(n);
	}

	// Convert n to a String List
	List<String> numerals = getNumerals(n);
	int numeralLength = numerals.size();

	for (int i = 0; i < numerals.size() - 1; i++)
	{
	    int digitPlace = (numeralLength - i) % 3;
	    // Get the numeral value
	    int primaryNumeral = Integer.parseInt(numerals.get(i));
	    int numeral = primaryNumeral;
	    	    
	    if (digitPlace == 2 && primaryNumeral == 1)
	    {
		// Numeral is between 10 - 19
		numeral = Integer.parseInt(numerals.get(i) + numerals.get(i + 1));
		// The next value is used
		i++;
		// Move into the DIGITS array
		digitPlace--;
	    }
	    
	    if (digitPlace == 0)
	    {
		digit.append(DIGITS[numeral] + " " + SCALE_DIGITS[primaryNumeral] + " ");
	    }
	    else if (digitPlace == 2)
	    {
		// Tens place
		digit.append(TENS_DIGITS[primaryNumeral] + " ");
	    }
	    else if (digitPlace == 1)
	    {
		// One place
		digit.append(DIGITS[numeral] + " ");
	    }
	}
	// Ten's digit so it is dealt with already
	if (numerals.size() != 2 || numerals.get(numerals.size() - 2).compareTo("1") != 0)
	{
	    int numeral = Integer.parseInt(numerals.get(numerals.size() - 1));
	    digit.append(DIGITS[numeral] + " ");
	}

	return digit.toString().trim();
    }

    /**
     * Get a List containing each digit in n. If n is negative the value at
     * index zero is "-".
     * 
     * @param n
     *            int
     * @return {@code List<String>}
     */
    public static List<String> getNumerals(int n)
    {
	// Convert n to a String
	char[] num = Integer.toString(n).toCharArray();
	List<String> digits = new ArrayList<String>();
	// Fill the List
	for (int i = 0; i < num.length; i++)
	{
	    digits.add(String.valueOf(num[i]));
	}
	return digits;
    }
}
