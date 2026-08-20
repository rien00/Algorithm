package Study_B1.SoloStudy;

public class prog_문자열곱하기 {
	public String solution(String my_string, int k) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < k; i++) {
			sb.append(my_string);
		}
		
		return sb.toString();
	}
}
