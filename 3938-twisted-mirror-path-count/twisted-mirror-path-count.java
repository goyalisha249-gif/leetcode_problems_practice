
class Solution {
    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1000000007;

        long[][][] dp = new long[m][n][2];

        // Starting position
        dp[0][0][0] = 1;

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                long right = dp[i][j][0];
                long down = dp[i][j][1];

                // Total ways to reach current cell
                long ways = (right + down) % MOD;

                if (grid[i][j] == 1) {

                    // Mirror: entered while moving right
                    // Robot moves down
                    if (i + 1 < m) {
                        dp[i + 1][j][1] =
                            (dp[i + 1][j][1] + right) % MOD;
                    }

                    // Mirror: entered while moving down
                    // Robot moves right
                    if (j + 1 < n) {
                        dp[i][j + 1][0] =
                            (dp[i][j + 1][0] + down) % MOD;
                    }

                } else {

                    // Empty cell: robot can move right
                    if (j + 1 < n) {
                        dp[i][j + 1][0] =
                            (dp[i][j + 1][0] + ways) % MOD;
                    }

                    // Empty cell: robot can move down
                    if (i + 1 < m) {
                        dp[i + 1][j][1] =
                            (dp[i + 1][j][1] + ways) % MOD;
                    }
                }
            }
        }

        return (int)((dp[m - 1][n - 1][0]
                    + dp[m - 1][n - 1][1]) % MOD);
    }
}
