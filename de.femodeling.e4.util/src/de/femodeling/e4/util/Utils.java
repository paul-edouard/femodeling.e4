package de.femodeling.e4.util;

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

}
