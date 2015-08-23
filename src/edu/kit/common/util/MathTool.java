package edu.kit.common.util;

import java.util.Random;
import java.util.regex.Pattern;

public class MathTool 
{
	public static double EPS = 1e-10;  // 精度范围

	public static int [] getRandom(int need,int range)
	{
		int [] rands=new int[need];
		Random random=new Random ();  
        boolean[] bool=new boolean[range];
        int randInt=0;  
        for(int i=0;i<need;i++)
        {
        	do
        		randInt=random.nextInt(range);  
        	while(bool[randInt]);
        	bool[randInt]=true;  
        	rands[i]=randInt;
        }
        return rands;
	}
	
	public static boolean isNumeric(String str)
	{ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	public static int getRandom(int range)
	{
		return getRandom(1, range)[0];
	}
	
	public static String getNumStr(String str)
	{
		return str.replaceAll("[^\\d]","");
	}
	
	public static boolean isInteger(double num)
	{
		if(num- (double)((int)num)<EPS)
			return true;
		return false;
	}
}
