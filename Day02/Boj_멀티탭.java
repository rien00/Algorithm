package Study_B1.Day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 백준 1700번. 멀티탭 스케줄링
 * (https://www.acmicpc.net/problem/1700)
 *
 * [문제 조건]
 * - 구멍이 N개인 멀티탭에 K개의 전자제품을 순서(order)대로 꽂아 사용한다.
 * - 이미 꽂혀 있는 제품은 다시 꽂을 필요가 없고, 빈 구멍이 있으면 그냥 꽂으면 된다.
 * - 구멍이 꽉 찬 상태에서 새 제품을 꽂아야 하면, 꽂혀 있는 제품 중 하나를 뽑아야 한다.
 * - 플러그를 뽑는 횟수의 최솟값을 구하라.
 *
 * [핵심 아이디어 - 그리디]
 * - 자리가 꽉 찼을 때 뽑을 제품을 고르는 기준:
 *   1) 앞으로 다시는 쓰이지 않는 제품이 있다면 그것을 뽑는다(어차피 재사용 안 되므로 손해가 없다).
 *   2) 모두 다시 쓰인다면, 그중 "가장 나중에" 다시 필요한 제품을 뽑는다.
 * - 미래에 가장 늦게 필요한 제품을 빼야, 그 사이에 다른 제품들을 뽑지 않고 버틸 수 있는
 *   기간이 가장 길어져 전체 재사용(뽑기) 횟수를 최소화할 수 있다는 것이 증명된 그리디 전략이다.
 *
 * [복잡도]
 * - 매 순간(최대 K번) 최대 N개의 후보에 대해 남은 순서(최대 K개)를 탐색하므로
 *   전체 시간복잡도는 O(K * N * K) = O(N * K^2) 수준이다.
 */
public class Boj_멀티탭 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 콘센트 구멍 개수
		int K = Integer.parseInt(st.nextToken()); // 전자제품을 꽂는 총 횟수(순서 개수)

		int[] order = new int[K]; // 시간 순서대로 꽂으려는 전자제품 번호
		st = new StringTokenizer(br.readLine());

		for(int i = 0; i < K; i++) {
			order[i] = Integer.parseInt(st.nextToken());
		}

		ArrayList<Integer> plug = new ArrayList<>(); // 현재 멀티탭에 꽂혀 있는 전자제품 목록
		int answer = 0; // 답: 플러그를 뽑은(=자리를 비운) 횟수

		for (int i =0; i < K; i++) {
			int device = order[i]; // 지금 시점(i)에 꽂아야 할 전자제품

			if ( plug.contains(device)) {
				continue; // 이미 꽂혀있으면 넘어간다.
			}

			if (plug.size() < N) {
				plug.add(device); // 빈 구멍이 있으면 꽂는다.
				continue;
			}

			// 여기부터는 자리가 꽉 찬 상태 -> 누군가를 뽑아야 함
			int removeIndex = -1; // 뽑을 대상의 plug 리스트 내 인덱스
			int farthest = -1;    // 지금까지 찾은 후보 중 "다음 사용 시점"이 가장 먼 값

			for(int j = 0; j < plug.size(); j++) {
				int candidate = plug.get(j); // 현재 꽂혀 있는 제품 하나
				int nextUse = -1; // candidate가 앞으로(i 이후) 다시 쓰이는 시점(인덱스)

				// candidate가 남은 순서(order[i+1..K-1]) 중 언제 또 나오는지 탐색
				for(int k = i + 1; k < K; k++) {
					if(order[k] == candidate) {
						nextUse = k;
						break;
					}
				}

				// 앞으로 다시 쓰이지 않는 제품을 발견하면, 더 볼 것도 없이 바로 그것을 뽑는다.
				if(nextUse == -1) {
					removeIndex = j;
					break;
				}

				// 앞으로 쓰이긴 하지만, 그 시점이 지금까지 후보 중 가장 늦다면 갱신
				if(nextUse > farthest) {
					farthest = nextUse;
					removeIndex = j;
				}
			}

			plug.set(removeIndex,device); // 선택된 자리를 뽑고 새 제품을 꽂음
			answer++; // 플러그를 뽑은 횟수 1 증가
		}
		System.out.println(answer);
	}
}
