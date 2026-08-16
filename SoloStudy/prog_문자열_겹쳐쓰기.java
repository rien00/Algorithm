package Study_B1.SoloStudy;

/**
 * 프로그래머스 181943. 문자열 겹쳐쓰기
 * (https://school.programmers.co.kr/learn/courses/30/lessons/181943)
 *
 * [문제 조건]
 * - my_string의 인덱스 s부터 overwrite_string 길이만큼을 overwrite_string으로
 *   덮어쓴 문자열을 리턴한다.
 * - 제한: 1 ≤ overwrite_string.length ≤ my_string.length ≤ 1,000
 *         0 ≤ s ≤ my_string.length - overwrite_string.length
 *   (s + overwrite_string.length가 항상 my_string 범위 안에 들어오도록 보장되므로
 *    별도의 경계값 체크가 필요 없다.)
 *
 * [핵심 아이디어]
 * - String은 불변 객체라 charAt(i) = 'x'처럼 특정 위치의 문자만 바꿔치기하는 문법이 없다.
 *   가변 컨테이너(char[] 또는 StringBuilder)로 옮겨서 수정한 뒤 다시 String으로 되돌려야 한다.
 * - my_string을 StringBuilder로 감싸고, overwrite_string의 j번째 문자를
 *   setCharAt(s + j, ...)로 원본의 (s + j) 위치에 덮어쓴다.
 * - 시작 인덱스는 s부터(전형적인 off-by-one 실수는 s-1부터 시작하는 것이므로 주의).
 *
 * [복잡도] N ≤ 1,000이라 시간복잡도는 신경 쓸 필요 없는 수준. 시간 O(N), 공간 O(N)
 */
public class prog_문자열_겹쳐쓰기 {
	public String solution(String my_string, String overwrite_string, int s) {
		StringBuilder sb = new StringBuilder(my_string);

		for (int j = 0; j < overwrite_string.length(); j++) {
			sb.setCharAt(s + j, overwrite_string.charAt(j));
		}

		return sb.toString();
	}
}
