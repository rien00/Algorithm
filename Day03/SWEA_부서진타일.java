package Study_B1.Day03;

import java.util.*;

public class SWEA_부서진타일 {
	/*
	 * 문제 요약
	 * 모든 '#' 칸을 서로 겹치지 않는 2x2 타일로 정확히 덮을 수 있는지 판단한다.
	 *
	 * 좌상단부터 순회할 때 아직 덮이지 않은 첫 '#'은 이전 타일의 일부가 될 수 없으므로,
	 * 반드시 새 2x2 타일의 왼쪽 위 칸이어야 한다. 이 성질을 이용해 즉시 배치 여부를 결정한다.
	 */

	public static void main(String[] args) {
		// 테스트 케이스 수를 먼저 입력받는다.
		Scanner sc = new Scanner(System.in);
		int TC = sc.nextInt();

		for(int t = 1; t <= TC; t++) {
			int N = sc.nextInt();
			int M = sc.nextInt();

			// '#'은 타일이 있어야 하는 칸, 나머지 문자는 비어 있는 칸을 의미한다.
			char[][] grid = new char[N][];
			for(int i=0; i < N; i++) {
				grid[i] = sc.next().toCharArray();
			}

			// covered[i][j]는 해당 '#' 칸이 이미 하나의 2x2 타일에 포함됐는지를 나타낸다.
			boolean[][] covered = new boolean[N][M];
			// 검사 도중 2x2 타일을 만들 수 없는 칸을 발견하면 false로 바꾼다.
			boolean possible = true;

			/*
			 * 위에서 아래, 왼쪽에서 오른쪽으로 순회한다.
			 * 아직 덮이지 않은 '#'을 만나면 그 칸은 새 2x2 타일의 왼쪽 위 칸이어야 한다.
			 */
			for(int i = 0; i < N && possible; i++) {
				for(int j = 0; j < M && possible; j++) {

						if(grid[i][j] == '#' && !covered[i][j]) {
							// 마지막 행이나 마지막 열에서는 2x2 타일을 만들 수 없다.
							if(i + 1 >= N || j + 1 >= M) {
								possible = false;
								continue;
							}

							// 오른쪽 칸이 '#'이 아니거나 이미 다른 타일이 사용했다면 배치할 수 없다.
							if(grid[i][j+1] != '#' || covered[i][j+1]) {
								possible = false;
								continue;
							}

							// 아래쪽 칸도 새 타일에 포함할 수 있는 '#'이어야 한다.
							if(grid[i+1][j] != '#' || covered[i+1][j]) {
								possible = false;
								continue;
							}

							// 오른쪽 아래 칸까지 조건을 만족해야 완전한 2x2 타일이 된다.
							if(grid[i+1][j+1] != '#' || covered[i+1][j+1]) {
								possible = false;
								continue;
							}

							// 네 칸이 모두 유효할 때만 하나의 2x2 타일로 덮었다고 표시한다.
							covered[i][j]     = true;
							covered[i][j+1]   = true;
							covered[i+1][j]   = true;
							covered[i+1][j+1] = true;
						}
					}
			}

			// SWEA 출력 형식: 모든 '#' 영역을 2x2 타일로 구성할 수 있으면 YES를 출력한다.
			System.out.println("#" + t + " " + (possible ? "YES" : "NO"));
		}

		// 의도한 알고리즘의 시간 복잡도는 격자를 한 번 순회하므로 O(N * M), 공간 복잡도도 O(N * M)이다.
	}
}
