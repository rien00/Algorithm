/**
 * 프로그래머스 154538. 저주의 숫자 3
 * (https://school.programmers.co.kr/learn/courses/30/lessons/154538)
 *
 * [문제 조건]
 * - 3의 배수이거나 숫자 3이 포함된 수는 "저주받은 숫자"로 보고 세지 않는다.
 * - 저주받지 않은 수만 1부터 순서대로 셀 때, n번째로 세어지는 수를 구하라.
 *
 * [핵심 아이디어]
 * - 후보(candidate)를 1씩 늘려가며 저주받은 숫자(3의 배수 또는 3 포함)는 건너뛰고,
 *   저주받지 않은 수를 찾을 때마다 count를 1 증가시킨다.
 * - count가 n에 도달하는 순간의 candidate가 정답이다.
 *
 * [containsThree] 숫자를 10으로 나누며 각 자리를 확인해 3이 포함되어 있는지 검사한다.
 *
 * [복잡도] 정답 후보까지 순차 탐색 O(candidate) — n이 작으므로 충분히 빠르다.
 */
public class prog_저주의숫자3 {

    public int solution(int n){

        int count = 0;      // 저주받지 않은 수를 센 개수
        int candidate = 0;  // 현재 검사 중인 후보 숫자

        while (count < n) {
            candidate++;

            // 3의 배수이거나 숫자에 3이 포함되면 저주받은 숫자이므로 건너뜀
            if(candidate % 3 == 0 || containsThree(candidate)) {
                continue;
            }
            count++; // 저주받지 않은 수를 찾았으므로 카운트 증가
        }

        return candidate;
    }

    // 숫자의 각 자리에 3이 포함되어 있는지 확인
    static boolean containsThree(int num) {
        while (num > 0) {
            if (num % 10 == 3) {
                return true;
            }

            num = num / 10;
        }
        return false;
    }
}
