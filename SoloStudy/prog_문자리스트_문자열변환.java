package Study_B1.SoloStudy;

public class prog_문자리스트_문자열변환 {

	public String solution(String[] arr) {
		StringBuilder sb = new StringBuilder();
		
		for ( int i = 0; i < arr.length; i++ ) {
//			sb += String.valueOf(arr[i].charAt(i));
			sb.append(arr[i]);
		}
		
		return sb.toString();
	}
}
