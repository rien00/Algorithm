package Study_B1.Day02;

import java.util.ArrayList;
import java.util.List;

/**
 * 프로그래머스 42586. 기능개발
 * (https://school.programmers.co.kr/learn/courses/30/lessons/42586)
 *
 * [문제 조건]
 * - 각 기능은 progresses[i](현재 진행률)에서 시작해 매일 speeds[i]%씩 진행되며,
 *   100% 이상이 되어야 배포 가능하다.
 * - 앞 기능이 아직 배포되지 않으면, 뒤 기능이 먼저 100%가 되어도 대기했다가
 *   앞 기능과 "함께" 배포된다(선입선출, 순서를 건너뛸 수 없음).
 * - 배포 그룹별로 몇 개의 기능이 한 번에 배포되는지를 순서대로 배열로 반환하라.
 *
 * [핵심 아이디어]
 * - 각 기능이 100%가 되기까지 걸리는 일수(days[i])를 올림 나눗셈으로 미리 계산한다.
 * - 앞에서부터 순서대로 훑으면서, 현재 배포 그룹의 기준일(standard, 그룹 내 가장 늦게
 *   끝나는 기능의 완료일)보다 먼저(또는 같이) 끝나는 기능은 같은 그룹으로 묶는다.
 * - 기준일보다 늦게 끝나는 기능을 만나면 그 시점에서 이전 그룹을 확정하고, 그 기능을
 *   기준으로 삼아 새 그룹을 시작한다.
 * - progresses가 진행률 오름차순이 아니어도 되는 이유: 배포는 "먼저 등록된 순서"대로
 *   진행되며, days 배열도 그 순서를 그대로 유지하므로 별도 정렬 없이 앞에서부터
 *   훑기만 하면 된다.
 *
 * [복잡도] 시간 O(N), 공간 O(N)
 */
public class prog_기능개발 {

	public int[] solution(int[] progresses, int[] speeds) {
		int N = progresses.length;
		int[] days = new int[N]; // days[i] = i번째 기능이 100%(배포 가능)가 되기까지 걸리는 일수

		for (int i = 0; i < N; i++) {
			// (100 - 현재 진행률) / 속도를 올림 처리한 값
			// = (100 - progresses[i] + speeds[i] - 1) / speeds[i]  (정수 나눗셈으로 ceil 구현)
			days[i] = (100 - progresses[i] + speeds[i] - 1) / speeds[i];
		}

		List<Integer> answer = new ArrayList<>(); // 각 배포일에 함께 나가는 기능 개수 목록

		int standard = days[0]; // 현재 배포 그룹의 기준 배포일(그룹 내 가장 먼저 완성되는, 즉 가장 오래 걸리는 기준)
		int count = 1;          // 현재 배포 그룹에 포함된 기능 개수

		for (int i = 1; i < N; i++) {
			if(days[i] <= standard) {
				count++; // 기준일보다 먼저(또는 같이) 끝나므로 같은 배포일 그룹에 포함
			} else {
				// 기준일보다 늦게 끝나는 기능을 만나면, 이전 그룹을 확정 짓고 새 그룹 시작
				answer.add(count);
				standard = days[i]; // 새 그룹의 기준을 이 기능의 완료일로 갱신
				count = 1;
			}
		}

		answer.add(count); // 마지막 그룹 추가

		int[] result = new int[answer.size()];
		for(int i = 0; i < result.length; i++) {
			result[i] = answer.get(i);
		}

		return result;
	}
}
