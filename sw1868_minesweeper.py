"""
SW Expert Academy 1868. 파핑파핑 지뢰찾기 (D4)
--------------------------------------------------
[문제 요약]
- N×N 표에 지뢰('*')와 빈 칸('.')이 있다.
- 빈 칸을 클릭하면 그 칸에 인접한 8칸 중 지뢰 개수(0~8)가 숫자로 표시된다.
- 표시된 숫자가 0이면, 인접한 8칸도 지뢰가 없다는 것이 확정되므로
  '자동으로' 연쇄적으로 숫자가 표시된다. (마치 실제 지뢰찾기 게임의 빈 칸 연쇄 오픈과 동일)
- 목표: 지뢰가 아닌 모든 칸에 숫자가 표시되도록 하는 '최소 클릭 횟수'를 구하라.

[핵심 아이디어]
1. 먼저 지뢰가 아닌 모든 칸에 대해 "인접 8방향 지뢰 개수"를 미리 계산해 둔다.
2. 값이 0인 칸을 클릭하면 주변이 연쇄적으로 열리므로,
   0인 칸들과 그 칸들로 인해 연쇄적으로 열리는 모든 칸을 BFS(너비 우선 탐색)로 한 번에 묶는다.
   → 이 연쇄 묶음 전체는 '단 한 번의 클릭'으로 처리된다.
3. BFS/플러드필로 처리되지 않고 남은(=0이 아니고, 연쇄에도 포함되지 않은) 칸들은
   각각 개별적으로 한 번씩 클릭해야만 숫자가 표시된다.
4. 따라서 정답 = (0에서 시작한 BFS 묶음의 개수) + (그 외 개별 클릭이 필요한 칸의 개수)

[구현상 주의점 - 오답 사례 방지]
- 두 개의 서로 다른 0인 칸이 서로의 인접 범위 안에서 연결되어 있다면
  (예: (2,2)가 0이고 그 인접 칸 중 (3,3)도 0인 경우),
  이 둘은 '같은 클릭'으로 묶여야 한다.
  → BFS 큐에 처음 발견한 0만 넣는 것이 아니라, 탐색 도중 발견하는
    모든 0인 칸도 큐에 추가해서 계속 이어서 탐색해야 한다.
  → 이 처리를 빠뜨리면 실제로는 1번 클릭으로 끝나는 영역을 2번 이상으로 잘못 세게 된다.
- BFS 시작 조건: '지뢰가 아니고', '아직 방문하지 않았고', '인접 지뢰 개수가 0'인 칸에서만
  새로운 클릭(click += 1)을 카운트한다. 이미 방문된 칸이면 새로 클릭할 필요가 없다.
- N ≤ 300 이므로 최악의 경우 90,000칸, 인접 탐색까지 포함해도 O(N^2)로 시간 제한 내에 충분히 처리 가능.
- 재귀(DFS)로 구현하면 최악의 경우 90,000 깊이까지 재귀가 들어갈 수 있어
  스택 오버플로우(재귀 깊이 제한) 위험이 크다. → 반드시 deque 기반 BFS(반복문)로 구현한다.

[복잡도]
- 시간복잡도: O(N^2)  (모든 칸을 한 번씩 방문 + 각 칸마다 8방향 탐색)
- 공간복잡도: O(N^2)  (지뢰 개수 배열, 방문 배열)
"""

import sys
from collections import deque


def solve() -> None:
    input_stream = sys.stdin
    read_line = input_stream.readline

    # 8방향(상하좌우 + 대각선) 이동 벡터
    dx = [-1, -1, -1, 0, 0, 1, 1, 1]
    dy = [-1, 0, 1, -1, 1, -1, 0, 1]

    T = int(read_line())  # 테스트 케이스 개수
    results = []

    for tc in range(1, T + 1):
        N = int(read_line())  # 표의 크기 (N × N)
        board = [read_line().strip() for _ in range(N)]

        # Step 1. 지뢰가 아닌 각 칸에 대해 인접 8칸 중 지뢰 개수를 미리 계산한다.
        mine_count = [[0] * N for _ in range(N)]
        for i in range(N):
            for j in range(N):
                if board[i][j] == '*':
                    continue  # 지뢰 칸은 계산 대상이 아니다.
                cnt = 0
                for d in range(8):
                    ni, nj = i + dx[d], j + dy[d]
                    if 0 <= ni < N and 0 <= nj < N and board[ni][nj] == '*':
                        cnt += 1
                mine_count[i][j] = cnt

        # 이미 숫자가 표시된(=클릭했거나, 연쇄로 열린) 칸인지 여부
        visited = [[False] * N for _ in range(N)]

        click = 0  # 최소 클릭 횟수 누적

        # Step 2. 인접 지뢰 개수가 0인 칸에서 BFS를 시작해 연쇄 오픈 영역을 한 번에 처리한다.
        for i in range(N):
            for j in range(N):
                # 지뢰이거나, 이미 방문했거나, 0이 아닌 칸은 여기서 새로 클릭을 시작할 필요 없음
                if board[i][j] == '*' or visited[i][j] or mine_count[i][j] != 0:
                    continue

                # 새로운 0 영역을 발견 → 이 영역 전체를 여는 클릭 1회 소모
                click += 1
                visited[i][j] = True
                queue = deque([(i, j)])

                while queue:
                    ci, cj = queue.popleft()
                    # 현재 칸이 0이므로, 인접한 8칸은 자동으로 전부 숫자가 표시(open)된다.
                    for d in range(8):
                        ni, nj = ci + dx[d], cj + dy[d]
                        if not (0 <= ni < N and 0 <= nj < N):
                            continue
                        if board[ni][nj] == '*' or visited[ni][nj]:
                            continue
                        visited[ni][nj] = True
                        # 새로 열린 칸도 0이라면, 같은 클릭에 이어서 연쇄적으로 계속 열어야 한다.
                        if mine_count[ni][nj] == 0:
                            queue.append((ni, nj))

        # Step 3. 연쇄 오픈으로 열리지 않고 남은(=0이 아니고 아직 미방문인) 칸들은
        #         각각 별도로 한 번씩 클릭해야 한다.
        for i in range(N):
            for j in range(N):
                if board[i][j] != '*' and not visited[i][j]:
                    click += 1

        results.append(f"#{tc} {click}")

    print("\n".join(results))


if __name__ == "__main__":
    solve()
