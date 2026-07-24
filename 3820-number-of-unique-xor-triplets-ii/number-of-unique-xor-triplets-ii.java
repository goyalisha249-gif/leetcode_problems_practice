class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAXX = 2048;

        boolean[][] dp = new boolean[4][MAXX];
        dp[0][0] = true;

        for (int v : nums) {
            boolean[][] ndp = new boolean[4][MAXX];

            // Copy current states (not using current index)
            for (int i = 0; i < 4; i++) {
                System.arraycopy(dp[i], 0, ndp[i], 0, MAXX);
            }

            // Use current index once
            for (int x = 0; x < MAXX; x++) {
                if (dp[0][x]) ndp[1][x ^ v] = true;
                if (dp[1][x]) ndp[2][x ^ v] = true;
                if (dp[2][x]) ndp[3][x ^ v] = true;
            }

            // Use current index twice (v ^ v = 0)
            for (int x = 0; x < MAXX; x++) {
                if (dp[0][x]) ndp[2][x] = true;
                if (dp[1][x]) ndp[3][x] = true;
            }

            // Use current index three times (v ^ v ^ v = v)
            for (int x = 0; x < MAXX; x++) {
                if (dp[0][x]) ndp[3][x ^ v] = true;
            }

            dp = ndp;
        }

        int ans = 0;
        for (boolean b : dp[3]) {
            if (b) ans++;
        }

        return ans;
    }
}