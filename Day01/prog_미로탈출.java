package Study_B1.Day01;

import java.util.*;

/**
 * 프로그래머스 159993. 미로 탈출
 * (https://school.programmers.co.kr/learn/courses/30/lessons/159993)
 *
 * [문제 조건]
 * - 격자 미로(maps)는 'S'(시작), 'E'(출구), 'L'(레버), 'O'(통로), 'X'(벽)로 구성된다.
 * - 이동 순서가 고정되어 있다: 반드시 S → L(레버를 당김) → E 순서로 도착해야 한다.
 *   (레버를 당기지 않아도 출구 칸은 그냥 지나갈 수 있지만, "탈출"이 인정되려면
 *    레버를 먼저 당긴 뒤에 출구에 도달해야 한다.)
 * - 한 칸 이동에 1초. 상하좌우로만 이동 가능(대각선 이동 불가), 벽은 지나갈 수 없다.
 * - S→L→E 전체에 걸리는 최소 시간을 구하라. 불가능하면 -1.
 *
 * [핵심 아이디어]
 * - 방문 순서가 S → L → E로 고정되어 있으므로, 전체 최단 경로는
 *   "S에서 L까지의 최단 거리" + "L에서 E까지의 최단 거리"로 정확히 분리된다.
 *   (중간 경유지 L을 반드시 거쳐야 하는 최단 경로 문제는, 가중치가 모두 1(=BFS로 최단거리
 *    계산 가능)인 그래프에서는 두 구간의 최단거리를 독립적으로 구해 더하면 되기 때문이다.
 *    즉 "S~L 사이에서 최적으로 움직인 경로"와 "L~E 사이에서 최적으로 움직인 경로"를
 *    이어붙인 것이 곧 전체 최적 경로가 된다.)
 * - 가중치 없는(모든 간선 비용 1) 그래프에서의 두 지점 간 최단 거리는 BFS로 구하는 것이
 *   정석이며, DFS나 다익스트라보다 훨씬 간단하고 빠르다.
 * - 따라서 BFS를 총 두 번(S→L, L→E) 호출해서 각각의 최단 거리를 구하고 합산한다.
 * - 둘 중 하나라도 도달 불가능(-1)하면 전체 경로도 성립하지 않으므로 즉시 -1을 반환한다.
 *
 * [복잡도]
 * - maps 크기: 최대 100 × 100 = 10,000칸
 * - BFS 한 번의 시간복잡도: O(n × m) (각 칸을 최대 한 번씩만 방문)
 * - BFS를 2번 호출하므로 전체 시간복잡도: O(n × m), 매우 여유로움.
 */
public class prog_미로탈출 {

	public int solution(String[] maps) {
		int n = maps.length, m = maps[0].length();         // n: 세로 길이(행 수), m: 가로 길이(열 수)
		int [] start = null, lever = null, exit = null;    // S, L, E 좌표를 담을 변수 (각 int[]{row, col})

		for (int i=0; i < n; i++) {                 // 모든 행(i) 순회
			for(int j = 0; j < m; j++) {             // 모든 열(j) 순회
				char c = maps[i].charAt(j);          // (i, j) 위치의 문자 하나 추출
				if(c == 'S') start = new int[] {i, j};       // 시작 지점 좌표 저장
				else if(c == 'L' ) lever = new int[] {i,j};  // 레버 좌표 저장
				else if (c == 'E') exit = new int[] {i,j};   // 출구 좌표 저장 (S/L/E는 각 1개씩만 존재)
			}
		}

		int d1 = bfs(maps, start, lever, n, m); // 1단계: S → L 최단 이동 시간
		if(d1 == -1) return -1;                 // 레버에 도달 불가하면 탈출 자체가 불가능

		int d2 = bfs(maps, lever, exit, n, m);  // 2단계: L → E 최단 이동 시간 (레버 당긴 뒤 출구까지)
		if(d2 == -1) return -1;                 // 출구에 도달 불가하면 탈출 불가능

		return d1 + d2; // S→L→E 순서 고정이므로 두 구간 최단시간의 합이 곧 전체 정답
	}


	private int bfs(String[] maps, int[] from, int[] to, int n, int m) {
		// from에서 to까지의 최단 거리(칸 수=시간)를 구하는 BFS. 벽('X') 통과 불가, 4방향 이동만 가능.

		int[][] dist = new int [n][m];               // dist[i][j] = from에서 (i,j)까지 최단 거리
		for(int[] row : dist) Arrays.fill(row, -1);   // 전부 -1로 초기화 ( -1 = 아직 방문 안 함 )

		Queue<int[]> q = new LinkedList<>();          // BFS용 큐 (좌표 {row, col} 저장)
		q.offer(from);                                // 시작 좌표를 큐에 삽입
		dist[from[0]][from[1]] = 0;                   // 시작점 자기 자신까지 거리는 0
		int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};  // 4방향 이동 벡터: 상, 하, 좌, 우

		while (!q.isEmpty()) {                                     // 큐가 빌 때까지 반복
			int[] cur = q.poll();                                  // 가장 가까운(먼저 들어온) 좌표 꺼냄

			if(cur[0] == to[0] && cur[1] == to[1]) return dist[cur[0]][cur[1]];
			// 목표(to)에 도달한 시점 → BFS 특성상 이 거리가 곧 최단 거리이므로 즉시 반환

			for(int d = 0; d < 4; d++) {                            // 4방향으로 한 칸씩 이동 시도
				int nx = cur[0] + dx[d], ny = cur[1] + dy[d];       // 이동한 다음 칸 좌표

				if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue; // 격자 범위 밖이면 건너뜀
				if(maps[nx].charAt(ny) == 'X') continue;             // 벽이면 통과 불가, 건너뜀
				if(dist[nx][ny] != -1) continue;                     // 이미 방문(=최단거리 확정)했으면 건너뜀

				dist[nx][ny] = dist[cur[0]][cur[1]] + 1; // 새 칸 발견: 현재 거리+1 (한 칸 이동=1초)
				q.offer(new int[] {nx, ny});             // 다음 탐색을 위해 큐에 추가
			}
		}

		return -1; // 큐가 다 빌 때까지 to에 도달 못했다면 경로 자체가 없다는 뜻
	}

}
