package Study_B1.Day02;

class Solution {
    public int solution(int[][] clockHands) {
        int N = clockHands.length;
        int totalCombos = (int) Math.pow(4, N); // 첫 번째 행 조합 개수
        int answer = Integer.MAX_VALUE;

        for (int start = 0; start < totalCombos; start++) {
            int[][] x = new int[N][N];

            // start를 4진법으로 해석해서 0번째 행을 채운다
            int temp = start;
            for (int j = 0; j < N; j++) {
                x[0][j] = temp % 4;
                temp /= 4;
            }

            // 위 행이 12시를 가리키도록 다음 행을 순서대로 결정
            for (int i = 0; i < N - 1; i++) {
                for (int j = 0; j < N; j++) {
                    int up = (i - 1 >= 0) ? x[i - 1][j] : 0;
                    int left = (j - 1 >= 0) ? x[i][j - 1] : 0;
                    int right = (j + 1 < N) ? x[i][j + 1] : 0;
                    int self = x[i][j];

                    int need = -(clockHands[i][j] + up + left + right + self);
                    x[i + 1][j] = ((need % 4) + 4) % 4; // 음수 보정
                }
            }

            // 마지막 행은 검증만 가능 (더 이상 결정할 행이 없음)
            boolean valid = true;
            for (int j = 0; j < N; j++) {
                int up = x[N - 2][j];
                int left = (j - 1 >= 0) ? x[N - 1][j - 1] : 0;
                int right = (j + 1 < N) ? x[N - 1][j + 1] : 0;
                int self = x[N - 1][j];

                int result = (clockHands[N - 1][j] + up + left + right + self) % 4;
                if (result != 0) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
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