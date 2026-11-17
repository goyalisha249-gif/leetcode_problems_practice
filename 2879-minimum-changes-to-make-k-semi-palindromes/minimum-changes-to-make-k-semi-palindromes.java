class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = Integer.MAX_VALUE / 2;

        // cost[i][j] = min changes to make s[i..j] a semi-palindrome
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;
                int best = INF;
                for (int d = 1; d < len; d++) {
                    if (len % d != 0) continue;
                    int changes = 0;
                    for (int g = 0; g < d; g++) {
                        int l = g;
                        int r = g + ((len - 1 - g) / d) * d;
                        while (l < r) {
                            if (s.charAt(i + l) != s.charAt(i + r)) changes++;
                            l += d;
                            r -= d;
                        }
                    }
                    best = Math.min(best, changes);
                }
                cost[i][j] = best;
            }
        }

        // dp[m][i] = min cost to split first i chars into m parts
        int[][] dp = new int[k + 1][n + 1];
        for (int[] row : dp) java.util.Arrays.fill(row, INF);
        dp[0][0] = 0;

        for (int m = 1; m <= k; m++) {
            for (int i = 2 * m; i <= n; i++) {
                for (int j = 2 * (m - 1); j < i - 1; j++) {
                    if (dp[m - 1][j] < INF) {
                        dp[m][i] = Math.min(dp[m][i], dp[m - 1][j] + cost[j][i - 1]);
                    }
                }
            }
        }
        return dp[k][n];
    }
}