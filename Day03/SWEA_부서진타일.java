package Study_B1.Day03;

import java.util.*;

public class SWEA_부서진타일 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int TC = sc.nextInt();
		
		for(int t = 1; t <= TC; t++) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			
			char[][] grid = new char[N][];
			for(int i=0; i < N; i++) {
				grid[i] = sc.next().toCharArray();
			}
			
			boolean[][] covered = new boolean[N][M];
			boolean possible = true;
			
			for(int i = 0; i < N && possible; i++) {
				for(int j = 0; j < M && possible; j++) {
					
					if(grid[i][j] == '#' && !covered[i][j]) {
						
						if(grid[i][j+1] != '#' || covered[i][j+1] == true) {
							possible = false;
							continue;
						}
							
						if(grid[i+1][j] != '#' || covered[i+1][j] == true) {
							possible = false;
							continue;
						}
						
						if(grid[i+1][j+1] != '#' || covered[i+1][j+1] == true) {
							possible = false;
							continue;
						}
					}
					covered[i][j]     = true;
					covered[i][j+1]   = true;
					covered[i+1][j]   = true;
					covered[i+1][j+1] = true;
				}
			}
			
			System.out.println("#" + t + " " + (possible ? "YES" : "NO"));
		}
		
		
	}
}
