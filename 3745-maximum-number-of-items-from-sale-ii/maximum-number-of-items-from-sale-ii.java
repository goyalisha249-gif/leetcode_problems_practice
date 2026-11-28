import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] cnt = new int[n + 1];
        for (int[] it : items) cnt[it[0]]++;

        // M[f] = number of items whose factor is a multiple of f
        int[] M = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            for (int k = f; k <= n; k += f) {
                M[f] += cnt[k];
            }
        }

        long pmin = Long.MAX_VALUE;
        for (int[] it : items) pmin = Math.min(pmin, it[1]);

        int[][] order = items.clone();
        Arrays.sort(order, (a, b) -> Integer.compare(a[1], b[1]));

        long rem = budget;
        long total = 0;
        for (int[] it : order) {
            long p = it[1];
            if (p >= 2 * pmin) break;
            long c = M[it[0]] - 1;          // other items this factor divides
            long t = Math.min(c, rem / p);
            total += 2 * t;
            rem -= t * p;
        }

        total += rem / pmin;                // plain copies of the cheapest item
        return (int) total;
    }
}