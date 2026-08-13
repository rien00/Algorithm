package Study_B1.Day03;

import java.util.*;

public class prog_베스트앨범 {
	public int[] solution(String[] genres, int[] plays) {
		int n = genres.length;
		
		Map<String, Integer> genreTotal = new HashMap<>();
		Map<String, List<int[]>> genreSongs = new HashMap<>();
		
		for (int i = 0; i < n; i++) {
			String genre = genres[i];
			
			genreTotal.put(genre, genreTotal.getOrDefault(genres[i], 0) + plays[i]);
			
			genreSongs.computeIfAbsent(genre, k -> new ArrayList<>());
			genreSongs.get(genre).add(new int[] {i, plays[i]});
			
		}
		
		List<String> sortedGenres = new ArrayList<>(genreTotal.keySet());
		
		sortedGenres.sort((g1, g2) -> genreTotal.get(g2) - genreTotal.get(g1));
		
		List<Integer> result = new ArrayList<>();
		
		for (String genre : sortedGenres) {
			List<int[]> songs = genreSongs.get(genre);
			
			songs.sort((a,b) -> {
				if (a[1] != b[1]) {
					return b[1] - a[1];
				}
				return a[0] - b[0];
			});
			
			
			int count = 0;
			for(int[] song : songs) {
				if (count >= 2) break;
				result.add(song[0]);
				count++;
			}
		}
		
		int[] answer = new int[result.size()];
		for(int i = 0; i < answer.length; i++) {
			answer[i] = result.get(i);
		}
		return answer;
	}
}
