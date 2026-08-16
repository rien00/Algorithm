package Study_B1.Day02;

import java.util.Arrays;

/**
 * 프로그래머스 42885. 구명보트
 * (https://school.programmers.co.kr/learn/courses/30/lessons/42885)
 *
 * [문제 조건]
 * - 보트 하나에 최대 2명까지 탈 수 있고, 태운 사람들의 몸무게 합이 limit을 넘으면 안 된다.
 * - 모든 사람을 구출하는 데 필요한 보트 수의 최솟값을 구하라.
 *
 * [핵심 아이디어 - 그리디 + 투 포인터]
 * - 몸무게를 오름차순 정렬한 뒤 양 끝(left=가장 가벼움, right=가장 무거움)에서 좁혀온다.
 * - 가장 무거운 사람(right)은 누구와 태우든 어차피 보트를 하나 써야 하므로 항상 태운다.
 * - 그 보트에 가장 가벼운 사람(left)까지 같이 태울 수 있으면(합이 limit 이하) 함께 태워서
 *   보트 한 척을 최대한 활용한다. 함께 못 태우면 right 혼자 보낸다.
 * - 가장 무거운 사람과 가장 가벼운 사람을 짝짓는 것이 보트 수를 최소화하는 최적 전략임이
 *   증명되어 있다(무거운 사람은 어차피 혼자라도 태워야 하므로, 남는 여유 중량을 가장
 *   가벼운 사람에게 써야 손해가 없다).
 *
 * [복잡도] 정렬 O(N log N) + 투 포인터 순회 O(N) → 전체 O(N log N)
 */
public class prog_구명보트 {
	public int solution(int[] people, int limit) {
        Arrays.sort(people); // 몸무게 오름차순 정렬

        int left = 0;                  // 가장 가벼운 사람
        int right = people.length - 1; // 가장 무거운 사람
        int boats = 0;                 // 필요한 보트 수

        while (left <= right) {
            // 가장 무거운 사람(right)과 가장 가벼운 사람(left)을 함께 태울 수 있으면 같이 태움
            // (left == right인 경우는 한 명만 남은 것이므로 같이 태우면 안 됨)
            if (left != right && people[right] + people[left] <= limit) {
                left++; // 가벼운 사람도 이 보트에 태웠으므로 다음 가벼운 사람으로 이동
            }
            right--; // 무거운 사람은 항상 보트를 타고 나감
            boats++; // 보트 1척 사용 
        }

        return boats;
    }
}
