
import java.util.*;

class Solution {
    public int numberOfSets(int n, int k) {

        final int MOD = 1000000007;

        long[][] dp = new long[n + 1][k + 1];

        // Base case: 0 segments
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int j = 1; j <= k; j++) {

            long prefix = 0;

            for (int i = 1; i <= n; i++) {

                // Add dp[i-1][j-1] to prefix
                // only when i-1 >= 1
                if (i >= 2) {
                    prefix = (prefix + dp[i - 1][j - 1]) % MOD;
                }

                // Case 1: No segment ends at i-1
                // Case 2: A segment ends at i-1
                dp[i][j] = (dp[i - 1][j] + prefix) % MOD;
            }
        }

        return (int) dp[n][k];
    }
}