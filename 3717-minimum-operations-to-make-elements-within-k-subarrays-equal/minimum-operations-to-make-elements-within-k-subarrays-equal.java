import java.util.*;

class Solution {
    private int m, LOG;
    private long[] cnt, sm;

    private void update(int pos, long dc, long ds) {
        for (; pos <= m; pos += pos & -pos) {
            cnt[pos] += dc;
            sm[pos] += ds;
        }
    }

    // returns {count, sum} over positions 1..pos
    private long[] query(int pos) {
        long c = 0, s = 0;
        for (; pos > 0; pos -= pos & -pos) {
            c += cnt[pos];
            s += sm[pos];
        }
        return new long[]{c, s};
    }

    // smallest position whose prefix count >= t
    private int kth(long t) {
        int pos = 0;
        for (int b = LOG; b >= 0; b--) {
            int nxt = pos + (1 << b);
            if (nxt <= m && cnt[nxt] < t) {
                pos = nxt;
                t -= cnt[nxt];
            }
        }
        return pos + 1;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        // coordinate compression
        int[] vals = Arrays.stream(nums).distinct().sorted().toArray();
        m = vals.length;
        LOG = 32 - Integer.numberOfLeadingZeros(m);
        cnt = new long[m + 1];
        sm = new long[m + 1];

        int[] idx = new int[n];
        for (int i = 0; i < n; i++) {
            idx[i] = Arrays.binarySearch(vals, nums[i]) + 1;
        }

        // cost[s] = cost to equalize window nums[s .. s+x-1]
        long[] cost = new long[n - x + 1];
        long total = 0;
        for (int i = 0; i < n; i++) {
            update(idx[i], 1, nums[i]);
            total += nums[i];
            if (i >= x) {
                update(idx[i - x], -1, -nums[i - x]);
                total -= nums[i - x];
            }
            if (i >= x - 1) {
                int pos = kth((x + 1) / 2);
                long med = vals[pos - 1];
                long[] q = query(pos);
                long cl = q[0], sl = q[1];
                cost[i - x + 1] = (med * cl - sl) + ((total - sl) - med * (x - cl));
            }
        }

        // dp over number of windows chosen
        final long INF = Long.MAX_VALUE / 4;
        long[] prev = new long[n + 1];          // j = 0: all zeros
        for (int j = 1; j <= k; j++) {
            long[] cur = new long[n + 1];
            Arrays.fill(cur, INF);
            for (int i = x * j; i <= n; i++) {
                cur[i] = Math.min(cur[i - 1], prev[i - x] + cost[i - x]);
            }
            prev = cur;
        }
        return prev[n];
    }
}