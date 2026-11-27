import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        final int MAXF = 1500;
        int[] cnt = new int[MAXF + 1];
        for (int[] it : items) cnt[it[0]]++;

        // multiples[v] = number of items whose factor is a multiple of v
        int[] multiples = new int[MAXF + 1];
        for (int v = 1; v <= MAXF; v++) {
            for (int m = v; m <= MAXF; m += v) {
                multiples[v] += cnt[m];
            }
        }

        int minPrice = Integer.MAX_VALUE;
        for (int[] it : items) minPrice = Math.min(minPrice, it[1]);

        // dp[b] = best total copies using at most b budget
        int[] dp = new int[budget + 1];

        // 0/1 knapsack over item types (buy at least one copy)
        for (int[] it : items) {
            int f = it[0], p = it[1];
            int val = multiples[f];   // 1 purchased + (multiples[f] - 1) free copies
            for (int b = budget; b >= p; b--) {
                dp[b] = Math.max(dp[b], dp[b - p] + val);
            }
        }

        // unbounded filler: extra copies of the cheapest item
        for (int b = minPrice; b <= budget; b++) {
            dp[b] = Math.max(dp[b], dp[b - minPrice] + 1);
        }

        return dp[budget];
    }
}