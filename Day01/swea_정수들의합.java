package Study_B1.Day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// SWEA "정수들의 합"
// 1~N 범위의 정수 중에서 뽑은 두 쌍 (a,b)와 (c,d)에 대해
// (a+b) - (c+d) = K 를 만족하는 경우의 수를 구하는 문제.
// a,b,c,d는 각각 1~N 사이의 값이며 (a+b), (c+d)는 각각 독립적으로 뽑는다고 가정.
// 브루트포스로 4중 for문을 돌리면 O(N^4)라 시간초과가 나므로,
// "두 수의 합이 s가 되는 경우의 수"를 먼저 표로 만들어 두고(O(N)),
// 그 표를 이용해 차이가 K인 합 쌍의 개수를 곱해서 더하는 방식(O(N))으로 최적화한다.
public class swea_정수들의합 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(br.readLine().trim()); // 테스트 케이스 개수
		StringBuilder sb = new StringBuilder();

		for(int tc = 0; tc < TC; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // 정수 범위: 1 ~ N
			int K = Integer.parseInt(st.nextToken()); // 두 합의 차이 (a+b) - (c+d) = K

			sb.append(solve(N, K)).append('\n');
		}
		System.out.print(sb);

	}

	private static long solve(int N, int K) {
		int maxSum = 2 * N; // 두 수(1~N)의 합이 가질 수 있는 최댓값
		long[] cnt = new long[maxSum + 1]; // cnt[s] = 두 수의 합이 s가 되는 (x, y) 순서쌍 개수

		//#1. 합이 s인 (a, b) 쌍의 개수를 미리 계산해서 채워 넣기
		// a는 1~N 범위이고 b = s - a도 1~N 범위여야 하므로
		// a의 가능한 최소/최대값(aMin~aMax)을 구해 그 개수를 센다.
		for (int s = 2; s <= maxSum; s++) {
			int aMin = Math.max(1, s - N);
			int aMax = Math.min(N, s - 1);
			cnt[s] = aMax - aMin + 1;
		}

		//#2. c+d = m일 때 a+b = m + K 가 되는 경우를 모두 더하기
		// 즉, (a+b) - (c+d) = K 를 만족하려면 a+b = (c+d) + K 여야 하므로,
		// 가능한 모든 m(=c+d)에 대해 cnt[m](c+d=m인 경우의 수) * cnt[m+K](a+b=m+K인 경우의 수)를 누적한다.
		long answer = 0;
		for(int m = 2; m <= maxSum; m++) {
			int target = m + K;
			if(target >= 2 && target <= maxSum) {
				answer += cnt[m] * cnt[target];
			}
		}

		return answer;
	}
}
