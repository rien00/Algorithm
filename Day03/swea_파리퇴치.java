package Study_B1.Day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class swea_파리퇴치 {
	/*
	 * 문제 요약
	 * 각 칸을 스프레이의 중심으로 삼아 + 모양과 X 모양으로 M칸만큼 분사했을 때
	 * 잡을 수 있는 파리 수를 계산하고, 그중 최댓값을 구한다.
	 *
	 * 두 모양은 탐색 방향만 다르므로 방향 배열을 매개변수로 넘겨 하나의 getSum으로 처리한다.
	 */
	// 4방향 이동 벡터: 같은 합산 메서드에 방향 배열만 바꿔 전달한다.
	// + 모양은 상하좌우, X 모양은 네 대각선 방향을 뜻한다.
    static int[] plusDr = {-1, 1, 0, 0};
    static int[] plusDc = {0, 0, -1, 1};
    static int[] crossDr = {-1, -1, 1, 1};
    static int[] crossDc = {-1, 1, -1, 1};

    public static void main(String[] args) throws IOException {
		// 입력량이 많으므로 Scanner보다 빠른 BufferedReader를 사용한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
		// 테스트 케이스마다 출력하지 않고 모아서 한 번에 출력해 I/O 비용을 줄인다.
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

			// map[r][c]는 해당 칸에 있는 파리 수를 의미한다.
			int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

			// 가능한 모든 칸을 스프레이의 중심으로 시도한다.
			int answer = 0;
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
					// 같은 중심에서 + 모양과 X 모양을 각각 계산한다.
					int plusSum = getSum(map, N, M, r, c, plusDr, plusDc);
                    int crossSum = getSum(map, N, M, r, c, crossDr, crossDc);
                    answer = Math.max(answer, Math.max(plusSum, crossSum));
                }
            }

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb);
    }

	// 중심 (r, c)에서 dr/dc 방향으로 M-1칸씩 뻗어나가며 잡는 파리 수를 구한다.
	// + / X 두 모양 모두 방향 벡터만 다르게 넘겨 처리하므로 합산 로직의 중복을 피한다.
    static int getSum(int[][] map, int N, int M, int r, int c, int[] dr, int[] dc) {
        int sum = map[r][c]; // 중심은 한 번만 더한다 (4방향 루프에 포함시키면 중복됨)

        for (int dir = 0; dir < 4; dir++) {
            for (int k = 1; k < M; k++) { // k=0은 중심이므로 1부터 시작
                int nr = r + dr[dir] * k;
                int nc = c + dc[dir] * k;

				// 격자를 벗어난 칸에는 파리가 없으므로 합산하지 않는다.
				if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                sum += map[nr][nc];
            }
        }
		// 중심 N^2개에서 두 모양의 4방향을 M칸씩 보므로 전체 시간 복잡도는 O(N^2 * M)이다.
		return sum;
    }
}
