package Study_B1.Day01;

import java.util.*;
import java.io.*;

/**
 * SW Expert Academy 1868. 파핑파핑 지뢰찾기 (D4)
 *
 * [문제 조건]
 * - N×N 표에 지뢰('*')와 빈 칸('.')이 있다.
 * - 빈 칸을 클릭하면 인접 8칸의 지뢰 개수(0~8)가 표시된다.
 * - 표시된 숫자가 0이면 인접 8칸도 자동으로 연쇄 오픈된다.
 * - 지뢰가 아닌 모든 칸에 숫자가 표시되기 위한 "최소 클릭 횟수"를 구하라.
 *
 * [풀이 전략 - 2단계]
 * 1단계) 지뢰 개수가 0인 칸에서 BFS로 연쇄 오픈되는 영역을 전부 묶는다.
 *        → 이런 영역 하나는 시작점 한 번의 클릭으로 전부 표시된다.
 * 2단계) BFS로 처리되지 않고 남은(=0이 아니며 아직 미방문인) 칸은
 *        연쇄 오픈이 되지 않으므로 각각 별도로 1번씩 클릭해야 한다.
 * 최종 정답 = (1단계에서 시작한 BFS 횟수) + (2단계에서 남은 칸의 개수)
 *
 * [핵심 함정 - BFS 큐 확장 조건]
 * - 어떤 0인 칸 A의 인접 칸 중에 또 다른 0인 칸 B가 있다면, A와 B는
 *   "같은 클릭"으로 묶여야 한다(A를 클릭하면 B도 자동으로 열리기 때문).
 * - 따라서 bfs()에서 새로 방문 처리한 칸이 count==0이면 반드시 큐에 추가해서
 *   같은 BFS 흐름 안에서 계속 이어 탐색해야 한다.
 *   이 처리가 빠지면(0인 칸을 발견해도 큐에 안 넣으면) 실제로는 클릭 1번으로
 *   끝나는 영역을 2번 이상으로 잘못 세게 된다.
 *   (예: 6×6 표에서 안쪽이 전부 뚫려 있고 0이 두 군데 연결된 경우 → 정답은 1인데
 *    이 처리를 놓치면 2로 오답 처리됨)
 * - visited 체크는 "지뢰가 아니고 && 아직 방문 안 한" 조건으로만 새 클릭을 카운트해야
 *   같은 영역을 중복으로 클릭 처리하지 않는다.
 * - 재귀 DFS 대신 반드시 Queue 기반 BFS(반복문)로 구현한다.
 *   N=300이면 최악의 경우 90,000칸이 한 번에 연쇄될 수 있어 재귀 DFS는
 *   스택 오버플로우 위험이 있다(문제의 스택 메모리 제한도 1MB로 타이트함).
 *
 * [복잡도] 시간 O(N^2), 공간 O(N^2)  (N ≤ 300 → 최악 90,000칸, 8방향 탐색 포함해도 충분히 빠름)
 */
public class swea_지뢰찾기 {
    static int N;                              // 표의 크기 (N × N). 테스트 케이스마다 갱신됨
    static char[][] board;                     // 원본 표. '*' = 지뢰, '.' = 빈 칸
    static int[][] count;                      // count[i][j] = (i,j)가 지뢰 아닐 때, 인접 8칸의 지뢰 개수
    static boolean[][] visited;                 // visited[i][j] = 이미 숫자가 표시된(클릭/연쇄 오픈) 칸인지 여부
    static int[] dx = {-1,-1,-1,0,0,1,1,1};     // 8방향 행 이동량 (좌상,상,우상,좌,우,좌하,하,우하)
    static int[] dy = {-1,0,1,-1,1,-1,0,1};     // dx와 짝지어 8방향 열 이동량

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 빠른 입력용 리더
        int T = Integer.parseInt(br.readLine().trim());                          // 테스트 케이스 개수
        StringBuilder sb = new StringBuilder();                                  // 결과를 모아 한 번에 출력할 버퍼

        for (int tc = 1; tc <= T; tc++) {                       // tc: 테스트 케이스 번호(1부터)
            N = Integer.parseInt(br.readLine().trim());         // 표 크기 N
            board = new char[N][];                              // 표 저장용 배열 (N행)
            for (int i = 0; i < N; i++) board[i] = br.readLine().toCharArray(); // 한 줄씩 읽어 문자 배열로 저장

            count = new int[N][N];       // 지뢰 개수 배열 (기본값 0으로 자동 초기화)
            visited = new boolean[N][N]; // 방문 여부 배열 (기본값 false로 자동 초기화)

            for (int i = 0; i < N; i++)                 // [Step 1] 모든 행
                for (int j = 0; j < N; j++)              // 모든 열에 대해
                    if (board[i][j] != '*')              // 지뢰가 아니면
                        count[i][j] = countMines(i, j);  // 인접 8칸 지뢰 개수 계산해 저장

            int clicks = 0; // 최소 클릭 횟수 누적 변수

            for (int i = 0; i < N; i++) {                              // [Step 2] 모든 칸을 순서대로 훑으며
                for (int j = 0; j < N; j++) {
                    if (board[i][j] != '*' && count[i][j] == 0 && !visited[i][j]) {
                        // 지뢰가 아니고, 인접 지뢰 0개, 아직 미방문 → 새로운 0-연쇄 영역의 시작점
                        clicks++;   // 이 영역을 여는 데 클릭 1회 소모
                        bfs(i, j);  // 연쇄적으로 열리는 모든 칸을 한 번에 visited 처리
                    }
                }
            }

            for (int i = 0; i < N; i++)                       // [Step 3] 다시 모든 행을 훑으며
                for (int j = 0; j < N; j++)                    // 모든 열을 훑으며
                    if (board[i][j] != '*' && !visited[i][j])  // 지뢰가 아니고 아직도 미방문이면
                        clicks++;                               // 개별 클릭이 필요하므로 1 증가

            sb.append("#").append(tc).append(" ").append(clicks).append("\n"); // "#tc 클릭수" 형식으로 저장
        }

        System.out.print(sb); // 모든 케이스 처리 후 한 번에 출력
    }

    static int countMines(int x, int y) {
        // (x, y)를 둘러싼 8방향 칸 중 지뢰('*') 개수를 세어 반환

        int cnt = 0; // 지뢰 개수 누적 변수
        for (int d = 0; d < 8; d++) {                        // 8방향을 순서대로 확인
            int nx = x + dx[d], ny = y + dy[d];              // d번째 방향으로 이동한 좌표
            if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue; // 범위 밖이면 건너뜀
            if (board[nx][ny] == '*') cnt++;                 // 지뢰면 개수 증가
        }
        return cnt; // 최종 지뢰 개수 반환
    }

    static void bfs(int sx, int sy) {
        // (sx, sy)는 count==0인 칸(연쇄 오픈 시작점). 여기서부터 연쇄적으로 열리는 모든 칸을 visited 처리.
        // 이 함수 호출 전체가 "클릭 1회"에 해당하는 연쇄 반응을 담당한다.

        Queue<int[]> q = new LinkedList<>(); // BFS용 큐 (좌표 {row, col})
        q.offer(new int[]{sx, sy});          // 시작 좌표 삽입
        visited[sx][sy] = true;              // 시작 칸 방문(표시) 처리

        while (!q.isEmpty()) {              // 큐가 빌 때까지(=더 열릴 칸이 없을 때까지) 반복
            int[] cur = q.poll();           // 좌표 하나 꺼냄
            int x = cur[0], y = cur[1];     // 행, 열로 분리

            for (int d = 0; d < 8; d++) {   // 현재 칸이 count==0이므로 인접 8칸은 전부 자동 오픈됨
                int nx = x + dx[d], ny = y + dy[d];                     // 이동한 다음 칸 좌표
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;   // 범위 밖이면 무시
                if (board[nx][ny] == '*' || visited[nx][ny]) continue; // 지뢰거나 이미 방문했으면 무시

                visited[nx][ny] = true; // 이 클릭으로 자동 오픈되므로 방문 처리

                if (count[nx][ny] == 0) q.offer(new int[]{nx, ny});
                // 새로 연 칸도 인접 지뢰 0개면 여기서 또 연쇄가 이어져야 하므로 같은 BFS(=같은 클릭)에서 계속 탐색
                // ※ 이 조건 누락 시, 서로 이어진 두 0-영역을 클릭 2번으로 잘못 세는 오답 원인이 됨
            }
        }
    }
}