package Study_B1.SoloStudy;

//public class prog_문자열_겹쳐쓰기 {
//	public String solution(String my_string, String overwrite_string, int s) {
//		
//		char[] arr = my_string.toCharArray();
//		
//		for(int j = 0; j < overwrite_string.length(); j++)	{
//			
//			arr[s + j] = overwrite_string.charAt(j);
//			}
//		
//		
//		return String.valueOf(arr);
//	}
//	
//}

class Solutoin {
	public String solution(String my_string, String overwrite_string, int s) {
		StringBuilder sb = new StringBuilder(my_string);
		
		for (int j = 0; j < overwrite_string.length(); j++) {
			sb.setCharAt(s + j, overwrite_string.charAt(j));
		}
		
		return sb.toString();
	}
}
