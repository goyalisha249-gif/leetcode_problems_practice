
class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        int LOG = 1;

        while ((1 << LOG) <= n) {
            LOG++;
        }

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Find next greater element for every index
        int[] next = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            next[i] = (top == -1) ? n : stack[top];
            stack[++top] = i;
        }

        // Binary lifting tables
        int[][] up = new int[LOG][n];
        long[][] cost = new long[LOG][n];

        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];

            cost[0][i] =
                (long) nums[i] * (next[i] - i)
                - (prefix[next[i]] - prefix[i]);
        }

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                int mid = up[j - 1][i];

                if (mid < n) {
                    up[j][i] = up[j - 1][mid];

                    cost[j][i] =
                        cost[j - 1][i] + cost[j - 1][mid];
                } else {
                    up[j][i] = n;
                    cost[j][i] = cost[j - 1][i];
                }
            }
        }

        // Calculate operations needed for [l, r]
        long ans = 0;

        for (int l = 0; l < n; l++) {
            int low = l;
            int high = n - 1;
            int best = l - 1;

            while (low <= high) {
                int mid = low + (high - low) / 2;

                int cur = l;
                long operations = 0;

                // Jump through complete maximum blocks
                for (int j = LOG - 1; j >= 0; j--) {
                    if (up[j][cur] <= mid) {
                        operations += cost[j][cur];
                        cur = up[j][cur];
                    }
                }

                // Calculate the remaining partial block
                operations += (long) nums[cur] * (mid - cur + 1)
                              - (prefix[mid + 1] - prefix[cur]);

                if (operations <= k) {
                    best = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            ans += best - l + 1;
        }

        return ans;
    }
}
