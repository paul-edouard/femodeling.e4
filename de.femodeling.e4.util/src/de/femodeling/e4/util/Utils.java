package de.femodeling.e4.util;

import java.util.HashSet;
import java.util.LinkedList;

public class Utils {

	public static boolean isWIndow() {
		return System.getenv().containsKey("windir");

	}

	public static String getUserName() {
		return System.getProperty("user.name");
	}

	public static void main(String args[]) {
		System.out.println(System.getProperty("user.name"));
	}
	
	public static HashSet<String> copyCollection(HashSet<String> input){
		HashSet<String> target=new HashSet<String>();
		for(String s:input)
			target.add(s);
		
		return target;
	}
	
	public static LinkedList<String> copyCollection(LinkedList<String> input){
		LinkedList<String> target=new LinkedList<String>();
		for(String s:input)
			target.add(s);
		
		return target;
	}

}
