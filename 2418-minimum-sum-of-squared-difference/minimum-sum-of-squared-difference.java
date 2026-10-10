
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];

        long k = (long) k1 + k2;
        long totalDiff = 0;
        int maxDiff = 0;

        // Step 1: Calculate absolute differences
        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            totalDiff += diff[i];
            maxDiff = Math.max(maxDiff, diff[i]);
        }

        // Step 2: If all differences can become zero
        if (k >= totalDiff) {
            return 0;
        }

        // Step 3: Binary search for the minimum threshold
        int low = 0, high = maxDiff;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long needed = 0;

            for (int d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int threshold = low;
        long used = 0;
        long answer = 0;

        // Step 4: Reduce every difference to the threshold
        for (int d : diff) {
            int reduced = Math.min(d, threshold);
            used += d - reduced;
            answer += (long) reduced * reduced;
        }

        // Step 5: Use remaining operations on threshold values
        long remaining = k - used;

        if (threshold > 0) {
            answer -= remaining * (2L * threshold - 1);
        }

        return answer;
    }
}