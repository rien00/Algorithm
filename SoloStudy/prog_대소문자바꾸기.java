package Study_B1.SoloStudy;

import java.util.*;

public class prog_대소문자바꾸기 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String a = sc.next();
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < a.length(); i++) {
			char c = a.charAt(i);
			if(Character.isUpperCase(c)) {
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(Character.toUpperCase(c));
			}
		}
		System.out.println(sb.toString());
		
	}

}
