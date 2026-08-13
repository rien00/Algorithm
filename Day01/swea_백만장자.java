package Study_B1.Day01;

import java.util.*;
import java.io.*;

/**
 * SW Expert Academy 1859. 백만장자 프로젝트 (D2)
 *
 * [문제 조건]
 * - N일간의 매매가가 주어진다.
 * - 하루에 최대 1개까지만 구매 가능(감시망 회피), 판매는 개수 제한 없음.
 * - 최대 이익을 구하라.
 *
 * [그리디 아이디어]
 * - 어떤 날짜 i에 물건을 산다면, 그 물건을 팔 수 있는 가장 좋은 시점은
 *   "i일 이후(자기 자신 포함) 중 최댓값"인 날이다.
 * - 즉 각 날짜마다 필요한 값은 "그 날짜 이후의 최댓값(suffix max)" 하나뿐이다.
 * - 배열을 뒤에서 앞으로 한 번만 훑으면서 suffix max를 실시간 갱신하면
 *   별도의 재탐색(max() 재호출) 없이 O(N)에 답을 구할 수 있다.
 *
 * [뒤에서부터 훑는 이유]
 * - 앞에서부터 풀려면 매 인덱스마다 "이 시점 이후의 최댓값"을 다시 구해야 해서 O(N^2)이 되어
 *   N=1,000,000 조건에서 시간초과가 난다.
 * - 뒤에서부터 훑으면 suffixMax 변수 하나로 즉시 갱신·비교가 가능해 O(N)으로 끝난다.
 *
 * [주의: long 사용 이유]
 * - 최대 이익의 상한은 N(최대 1,000,000) × 가격(최대 10,000) ≈ 10^10 수준이라
 *   int(최대 약 21억) 범위를 넘는다. profit을 long으로 선언하지 않으면
 *   테스트케이스 일부(큰 값)에서만 틀리는 형태로 실패한다(부분 정답 7~8/10 패턴의 주 원인).
 *
 * [복잡도] 시간 O(N), 공간 O(N)
 */
public class swea_백만장자 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 빠른 입력용 리더 (N≤100만이라 필수)
        int T = Integer.parseInt(br.readLine().trim());                          // 테스트 케이스 개수
        StringBuilder sb = new StringBuilder();                                  // 결과를 모아 한 번에 출력할 버퍼

        for (int tc = 1; tc <= T; tc++) {                                        // tc: 테스트 케이스 번호(1부터)
            int n = Integer.parseInt(br.readLine().trim());                      // 날짜 수 (1 ≤ n ≤ 1,000,000)
            StringTokenizer st = new StringTokenizer(br.readLine());             // 공백 구분 매매가 문자열 토큰화
            int[] price = new int[n];                                           // 일자별 매매가 저장 배열
            for (int i = 0; i < n; i++) price[i] = Integer.parseInt(st.nextToken()); // 토큰을 순서대로 price에 저장

            long profit = 0;                // 누적 이익 (long: 최대 약 10^10까지 가능 → int면 오버플로우)
            int suffixMax = price[n - 1];   // suffixMax = i일 이후(자기 포함) 최댓값, 마지막 날 값으로 초기화

            for (int i = n - 2; i >= 0; i--) {          // 뒤에서 두 번째 날부터 첫째 날까지 역순 순회
                if (price[i] < suffixMax) {             // 오늘 가격이 미래 최댓값보다 싸면
                    profit += suffixMax - price[i];     // 오늘 사서 suffixMax 시점에 판다고 가정, 차익 누적
                } else {                                 // 오늘 가격이 지금까지 최댓값 이상이면
                    suffixMax = price[i];                // 오늘을 새로운 "미래 최댓값" 기준으로 갱신 (사지 않음)
                }
            }
            sb.append("#").append(tc).append(" ").append(profit).append("\n"); // "#tc 이익" 형식으로 결과 저장
        }
        System.out.print(sb); // 모든 케이스 처리 후 한 번에 출력 (성능 최적화)
    }
}