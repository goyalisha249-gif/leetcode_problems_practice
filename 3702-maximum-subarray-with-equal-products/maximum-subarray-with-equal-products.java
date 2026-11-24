class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int best = 0;
        // lcm of values 1..10 is 2520 and gcd <= 10, so lcm * gcd <= 25200.
        // The product never decreases as the subarray grows, so once it
        // exceeds this bound, no longer subarray from this start can match.
        final long LIMIT = 2520L * 10;

        for (int i = 0; i < n; i++) {
            long prod = 1, g = 0, l = 1;
            for (int j = i; j < n; j++) {
                int x = nums[j];
                prod *= x;
                if (prod > LIMIT) break;
                g = gcd(g, x);
                l = l / gcd(l, x) * x;
                if (prod == l * g) {
                    best = Math.max(best, j - i + 1);
                }
            }
        }
        return best;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}