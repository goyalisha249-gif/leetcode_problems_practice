import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        final long M1 = 1_000_000_007L, M2 = 998_244_353L;
        Random rnd = new Random();
        long B1 = 100_000 + rnd.nextInt(900_000_000);
        long B2 = 100_000 + rnd.nextInt(900_000_000);

        long[] h1 = new long[n + 1], h2 = new long[n + 1];
        long[] p1 = new long[n + 1], p2 = new long[n + 1];
        p1[0] = 1;
        p2[0] = 1;
        for (int i = 0; i < n; i++) {
            h1[i + 1] = (h1[i] * B1 + nums[i]) % M1;
            h2[i + 1] = (h2[i] * B2 + nums[i]) % M2;
            p1[i + 1] = p1[i] * B1 % M1;
            p2[i + 1] = p2[i] * B2 % M2;
        }

        int lo = 1, hi = n; // length n is always unique
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (hasUnique(mid, n, h1, h2, p1, p2, M1, M2)) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    private boolean hasUnique(int L, int n, long[] h1, long[] h2,
                              long[] p1, long[] p2, long M1, long M2) {
        int m = n - L + 1;
        long[] keys = new long[m];
        for (int i = 0; i < m; i++) {
            long a = ((h1[i + L] - h1[i] * p1[L] % M1) % M1 + M1) % M1;
            long b = ((h2[i + L] - h2[i] * p2[L] % M2) % M2 + M2) % M2;
            keys[i] = a * M2 + b;
        }
        Arrays.sort(keys);
        for (int i = 0; i < m; i++) {
            boolean diffPrev = (i == 0) || keys[i] != keys[i - 1];
            boolean diffNext = (i == m - 1) || keys[i] != keys[i + 1];
            if (diffPrev && diffNext) return true;
        }
        return false;
    }
}