package Study_B1.SoloStudy;

public class prog_문자열섞기 {

	public String solution(String str1, String str2) {
		
//		String[] arr = new String[str1.length() + str2.length()];
//		
//		for(int i = 0; i < arr.length; i++) {
//			if (i % 2 == 0) {
//				arr[i] = String.valueOf(str2.charAt(i / 2));
//			} else {
//				arr[i] = String.valueOf(str1.charAt(i / 2));
//			}
//		}
//
//		return String.join("", arr);
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < str1.length(); i++) {
			sb.append(str1.charAt(i)).append(str2.charAt(i));
		}
		
		return sb.toString();
		
	}
	
}
