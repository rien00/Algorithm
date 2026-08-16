package Study_B1.SoloStudy;

import java.util.*;

/**
 * 프로그래머스 - 대소문자 바꿔서 출력하기 (개인 연습)
 *
 * [문제 조건]
 * - 알파벳 문자열을 입력받아 대문자는 소문자로, 소문자는 대문자로 바꿔서 출력한다.
 *   예) aBcDeFg -> AbCdEfG
 *
 * [핵심 아이디어]
 * - String은 불변(immutable) 객체라 a += x 를 반복하면 그때마다 새 String 객체를
 *   생성해 복사한다. 반복문 안에서 N번 이어 붙이면 총 비용이 O(N^2)까지 커진다.
 * - 대신 가변 버퍼인 StringBuilder에 append하면 새로 만들지 않고 그 자리에서 이어 붙여
 *   총 비용이 O(N)으로 줄어든다. "결과를 누적해서 만드는" 패턴에서는 처음부터
 *   StringBuilder를 쓰는 것이 맞다.
 * - Character.isUpperCase/toUpperCase/toLowerCase는 char 하나 단위로 동작하므로,
 *   charAt(i)로 인덱스 i의 문자를 하나씩 꺼내 검사한 뒤 반대 대소문자로 바꿔 append한다.
 *
 * [복잡도] 시간 O(N), 공간 O(N)  (N = 문자열 길이)
 */
public class prog_대소문자바꾸기 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String a = sc.next();
		StringBuilder sb = new StringBuilder(); // 변환 결과를 누적할 가변 버퍼

		for(int i = 0; i < a.length(); i++) {
			char c = a.charAt(i); // i번째 문자 하나를 꺼내 검사
			if(Character.isUpperCase(c)) {
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(Character.toUpperCase(c));
			}
		}
		System.out.println(sb.toString());

	}

}
