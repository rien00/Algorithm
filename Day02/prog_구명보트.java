package Study_B1.Day02;

import java.util.Arrays;

// 프로그래머스 "구명보트"
// 보트 하나에 최대 2명까지 탈 수 있고, 태운 사람들의 몸무게 합이 limit을 넘으면 안 될 때
// 모든 사람을 구출하는 데 필요한 최소 보트 수를 구하는 문제.
// 전략(그리디 + 투 포인터): 몸무게를 정렬한 뒤
//   - 가장 무거운 사람(right)은 항상 보트를 하나 써야 하므로 태운다.
//   - 그 보트에 가장 가벼운 사람(left)까지 같이 태울 수 있으면(합이 limit 이하) 함께 태운다.
// 이렇게 무거운 사람과 가벼운 사람을 짝지어 보내면 보트 수를 최소화할 수 있다.
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
