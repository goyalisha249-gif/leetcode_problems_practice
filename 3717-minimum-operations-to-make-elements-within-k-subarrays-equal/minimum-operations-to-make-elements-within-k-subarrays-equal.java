
import java.util.*;

class Solution {

    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    long sumLeft = 0, sumRight = 0;
    int sizeLeft = 0, sizeRight = 0;
    int target;

    void addMap(TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    void removeMap(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);

        if (count == 1)
            map.remove(val);
        else
            map.put(val, count - 1);
    }

    void add(int val) {
        if (sizeLeft == 0 || val <= left.lastKey()) {
            addMap(left, val);
            sumLeft += val;
            sizeLeft++;
        } else {
            addMap(right, val);
            sumRight += val;
            sizeRight++;
        }

        balance();
    }

    void remove(int val) {
        if (left.containsKey(val)) {
            removeMap(left, val);
            sumLeft -= val;
            sizeLeft--;
        } else {
            removeMap(right, val);
            sumRight -= val;
            sizeRight--;
        }

        balance();
    }

    void balance() {

        while (sizeLeft > target) {
            int val = left.lastKey();

            removeMap(left, val);
            sumLeft -= val;
            sizeLeft--;

            addMap(right, val);
            sumRight += val;
            sizeRight++;
        }

        while (sizeLeft < target && sizeRight > 0) {
            int val = right.firstKey();

            removeMap(right, val);
            sumRight -= val;
            sizeRight--;

            addMap(left, val);
            sumLeft += val;
            sizeLeft++;
        }
    }

    long getCost() {
        long median = left.lastKey();

        return median * sizeLeft - sumLeft
             + sumRight - median * sizeRight;
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;
        target = (x + 1) / 2;

        long[] cost = new long[n - x + 1];

        for (int i = 0; i < x; i++) {
            add(nums[i]);
        }

        cost[0] = getCost();

        for (int i = x; i < n; i++) {
            remove(nums[i - x]);
            add(nums[i]);

            cost[i - x + 1] = getCost();
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {

                dp[j][i] = dp[j][i - 1];

                if (i >= x && dp[j - 1][i - x] != INF) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
