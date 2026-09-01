import java.util.*;

class Solution {

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litterId = new int[m][n];

        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        int sr = 0, sc = 0;
        int litterCount = 0;

        // Find S and assign IDs to L
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                }

                if (ch == 'L') {
                    litterId[i][j] = litterCount;
                    litterCount++;
                }
            }
        }

        // If there are k litter cells, masks go from 0 to 2^k - 1
        int allCollected = (1 << litterCount) - 1;

        /*
            State:
            [row, col, remainingEnergy, mask, moves]
        */
        Queue<int[]> queue = new LinkedList<>();

        boolean[][][][] visited =
                new boolean[m][n][energy + 1][1 << litterCount];

        queue.offer(new int[]{sr, sc, energy, 0, 0});

        visited[sr][sc][energy][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int r = current[0];
            int c = current[1];
            int e = current[2];
            int mask = current[3];
            int moves = current[4];

            // All litter collected
            if (mask == allCollected) {
                return moves;
            }

            // Try all 4 directions
            for (int d = 0; d < 4; d++) {

                int nr = r + dr[d];
                int nc = c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Cannot move if energy is 0
                if (e == 0) {
                    continue;
                }

                // Moving costs 1 energy
                int newEnergy = e - 1;

                // Keep current litter mask
                int newMask = mask;

                // If we enter litter
                if (classroom[nr].charAt(nc) == 'L') {

                    int id = litterId[nr][nc];

                    newMask = mask | (1 << id);
                }

                // If we enter reset area
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                // Visit this state if not visited before
                if (!visited[nr][nc][newEnergy][newMask]) {

                    visited[nr][nc][newEnergy][newMask] = true;

                    queue.offer(new int[]{
                            nr,
                            nc,
                            newEnergy,
                            newMask,
                            moves + 1
                    });
                }
            }
        }

        return -1;
    }
}