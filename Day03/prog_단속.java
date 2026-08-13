package Study_B1.Day03;

import java.util.Arrays;

public class prog_단속 {

	public int solution(int[][] routes) {
//		1. 진출 지점(routes[i][1] 오름차순 정렬)
		Arrays.sort(routes, (a,b) -> Integer.compare(a[1], b[1]));
		
		int camera = Integer.MIN_VALUE;
		int count = 0;
		
		for (int i = 0; i < routes.length; i++) {
			if(routes[i][0] > camera) {
				camera = routes[i][1];
				count++;
			}
		}
		
		return count;
		
	}
}
