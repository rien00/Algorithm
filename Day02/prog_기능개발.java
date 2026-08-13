package Study_B1.Day02;

import java.util.ArrayList;
import java.util.List;

// 프로그래머스 "기능개발"
// 각 기능은 progresses[i](현재 진행률)에서 시작해 매일 speeds[i]%씩 진행되며,
// 100% 이상이 되어야 배포 가능하다. 단, 앞 기능이 아직 배포되지 않으면
// 뒤 기능이 먼저 100%가 되어도 대기했다가 앞 기능과 "함께" 배포된다.
// -> 배포 그룹별로 몇 개의 기능이 한 번에 배포되는지 구하는 문제.
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
