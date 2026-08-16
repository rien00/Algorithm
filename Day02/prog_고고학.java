package Study_B1.Day02;

/**
 * 프로그래머스 "고고학" - N x N 격자의 다이얼 맞추기
 *
 * [문제 조건]
 * - N x N 격자의 각 칸에 다이얼(0~3, 4방향 중 하나)이 있고, clockHands[i][j]는
 *   그 칸을 0으로 맞추는 데 필요한 상태를 나타낸다.
 * - (i, j) 스위치를 누르면 자기 자신과 상하좌우 4개 이웃 칸의 다이얼이 함께 +1(90도)씩 돌아간다.
 *   즉 각 칸의 최종 값은 "그 칸 자신 + 상하좌우 이웃 스위치를 누른 횟수의 합"으로 결정된다.
 * - 모든 칸을 0(mod 4)으로 맞추는 데 필요한 스위치 총 클릭 수의 최솟값을 구하라.
 *
 * [핵심 아이디어 - 첫 행 브루트포스 + 나머지 행 결정]
 * - 스위치 (i, j)를 x[i][j]번(0~3, 4번 누르면 원위치이므로 mod 4로 충분) 누른다고 하면,
 *   칸 (i, j)가 0이 되려면 다음이 성립해야 한다(모두 mod 4):
 *       clockHands[i][j] + x[i-1][j] + x[i+1][j] + x[i][j-1] + x[i][j+1] + x[i][j] ≡ 0
 *   (격자 범위를 벗어난 이웃은 0으로 취급)
 * - 0번째 행의 x[0][j] 값들이 정해지면, 그 아래 행은 "바로 위 행의 방정식"으로 결정된다.
 *   i번째 행의 방정식에서 유일한 미지수는 x[i+1][j]뿐이므로
 *       x[i+1][j] = -(clockHands[i][j] + up + left + right + self)  (mod 4)
 *   로 즉시 계산할 수 있다(마지막 행 아래에는 x[N][j]가 없으므로 이 식은 적용하지 않는다).
 * - 따라서 0번째 행의 4^N가지 조합을 모두 브루트포스로 시도하면서, 매번 나머지 행을
 *   결정론적으로 채우고, 마지막(N-1번째) 행의 방정식이 실제로 만족되는지만 검증하면 된다.
 *   (마지막 행은 그 아래에 결정할 행이 없어 별도의 유효성 검사가 필요하다)
 * - 유효한 조합들 중 전체 스위치 클릭 수 합(sum of x[i][j])이 최소인 값을 답으로 한다.
 *
 * [복잡도]
 * - 시도하는 첫 행 조합 수: 4^N, 조합마다 O(N^2)만큼 나머지 행을 채우고 검증하므로
 *   전체 시간복잡도는 O(4^N * N^2)이다. N이 작다는 전제(대략 N ≤ 6~7 수준)에서만 통과 가능한 크기다.
 */
public class prog_고고학 {
    public int solution(int[][] clockHands) {
        int N = clockHands.length;
        int totalCombos = (int) Math.pow(4, N); // 0번째 행에 대해 시도할 모든 조합 수(칸마다 0~3, 총 N칸)
        int answer = Integer.MAX_VALUE;

        for (int start = 0; start < totalCombos; start++) {
            int[][] x = new int[N][N]; // x[i][j] = (i, j) 스위치를 누른 횟수(0~3)

            // start를 4진법으로 해석해서 0번째 행 x[0][0..N-1]을 채운다
            int temp = start;
            for (int j = 0; j < N; j++) {
                x[0][j] = temp % 4;
                temp /= 4;
            }

            // 0번째 행이 정해졌으므로, i번째 행의 방정식을 이용해 (i+1)번째 행을 한 줄씩 결정
            for (int i = 0; i < N - 1; i++) {
                for (int j = 0; j < N; j++) {
                    int up = (i - 1 >= 0) ? x[i - 1][j] : 0;   // 위 스위치가 이미 누른 횟수(범위 밖이면 0)
                    int left = (j - 1 >= 0) ? x[i][j - 1] : 0; // 왼쪽 스위치
                    int right = (j + 1 < N) ? x[i][j + 1] : 0; // 오른쪽 스위치
                    int self = x[i][j];                        // 자기 자신 스위치

                    // 이 칸이 0이 되려면 clockHands[i][j] + up + left + right + self + x[i+1][j] ≡ 0 (mod 4)
                    // 이어야 하므로, 유일한 미지수 x[i+1][j]를 역산한다.
                    int need = -(clockHands[i][j] + up + left + right + self);
                    x[i + 1][j] = ((need % 4) + 4) % 4; // 자바의 % 연산은 음수를 반환할 수 있어 보정
                }
            }

            // 마지막 행은 더 이상 결정할 다음 행이 없으므로, 이 조합이 실제로 유효한지 검증만 한다.
            boolean valid = true;
            for (int j = 0; j < N; j++) {
                int up = x[N - 2][j];
                int left = (j - 1 >= 0) ? x[N - 1][j - 1] : 0;
                int right = (j + 1 < N) ? x[N - 1][j + 1] : 0;
                int self = x[N - 1][j];

                int result = (clockHands[N - 1][j] + up + left + right + self) % 4;
                if (result != 0) {
                    valid = false; // 이 시작 조합으로는 마지막 행을 0으로 맞출 수 없음
                    break;
                }
            }

            if (valid) {
                // 유효한 조합이면 전체 스위치 클릭 수(=답 후보)를 계산해 최솟값을 갱신
                int sum = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        sum += x[i][j];
                    }
                }
                answer = Math.min(answer, sum);
            }
        }

        return answer;
    }
}
