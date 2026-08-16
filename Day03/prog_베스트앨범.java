package Study_B1.Day03;

import java.util.*;

public class prog_베스트앨범 {
	/*
	 * 문제 요약
	 * 1. 전체 재생 수가 많은 장르를 먼저 선택한다.
	 * 2. 같은 장르에서는 재생 수가 많은 곡을 우선한다.
	 * 3. 재생 수까지 같다면 고유 번호가 작은 곡을 우선하며, 장르당 최대 두 곡만 담는다.
	 *
	 * 따라서 "장르별 합계"와 "장르별 곡 목록"을 따로 관리한 뒤,
	 * 장르 순서와 장르 내부 곡 순서를 각각 정렬하는 것이 핵심이다.
	 */
	public int[] solution(String[] genres, int[] plays) {
		// genres와 plays의 같은 인덱스는 같은 곡을 나타낸다.
		int n = genres.length;
		
		// 장르별 전체 재생 횟수: 장르를 먼저 어떤 순서로 출력할지 결정할 때 사용한다.
		Map<String, Integer> genreTotal = new HashMap<>();
		// 장르별 곡 목록: 각 int[]는 {고유 번호(index), 재생 횟수}를 저장한다.
		Map<String, List<int[]>> genreSongs = new HashMap<>();
		
		// 한 번의 순회로 장르 합계와 장르별 곡 목록을 동시에 만든다.
		for (int i = 0; i < n; i++) {
			String genre = genres[i];
			
			// getOrDefault를 사용하면 처음 등장한 장르도 0에서 안전하게 누적할 수 있다.
			genreTotal.put(genre, genreTotal.getOrDefault(genres[i], 0) + plays[i]);
			
			// 해당 장르의 리스트가 없을 때만 새 ArrayList를 생성한다.
			genreSongs.computeIfAbsent(genre, k -> new ArrayList<>());
			genreSongs.get(genre).add(new int[] {i, plays[i]});
			
		}
		
		// Map의 keySet에는 순서가 없으므로 정렬 가능한 List로 옮긴다.
		List<String> sortedGenres = new ArrayList<>(genreTotal.keySet());
		
		// 전체 재생 횟수가 많은 장르부터 처리한다.
		sortedGenres.sort((g1, g2) -> genreTotal.get(g2) - genreTotal.get(g1));
		
		// 정답 크기는 장르별 곡 수에 따라 달라지므로 가변 길이 List에 먼저 저장한다.
		List<Integer> result = new ArrayList<>();
		
		for (String genre : sortedGenres) {
			List<int[]> songs = genreSongs.get(genre);
			
			/*
			 * 장르 내부 정렬 기준
			 * 1) 재생 횟수 내림차순
			 * 2) 재생 횟수가 같으면 고유 번호 오름차순
			 */
			songs.sort((a,b) -> {
				if (a[1] != b[1]) {
					return b[1] - a[1];
				}
				return a[0] - b[0];
			});
			
			
			// 베스트 앨범에는 한 장르에서 최대 두 곡만 수록한다.
			int count = 0;
			for(int[] song : songs) {
				if (count >= 2) break;
				result.add(song[0]);
				count++;
			}
		}
		
		// 프로그래머스 반환형에 맞게 List<Integer>를 int[]로 변환한다.
		int[] answer = new int[result.size()];
		for(int i = 0; i < answer.length; i++) {
			answer[i] = result.get(i);
		}
		// 전체 시간 복잡도는 곡 정렬이 지배하므로 최악의 경우 O(N log N)이다.
		return answer;
	}
}
