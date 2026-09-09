class Solution {
    public long countCommas(long n) {
        long ans = 0;

        // 1,000 to 999,999 -> 1 comma each
        if (n >= 1000) {
            ans += Math.min(n, 999999L) - 1000 + 1;
        }

        // 1,000,000 to 999,999,999 -> 2 commas each
        if (n >= 1000000) {
            ans += (Math.min(n, 999999999L) - 1000000 + 1) * 2;
        }

        // 1,000,000,000 to 999,999,999,999 -> 3 commas each
        if (n >= 1000000000L) {
            ans += (Math.min(n, 999999999999L) - 1000000000L + 1) * 3;
        }

        // 1,000,000,000,000 to 999,999,999,999,999 -> 4 commas each
        if (n >= 1000000000000L) {
            ans += (Math.min(n, 999999999999999L) - 1000000000000L + 1) * 4;
        }

        // 1,000,000,000,000,000 to 10^15 -> 5 commas each
        if (n >= 1000000000000000L) {
            ans += (n - 1000000000000000L + 1) * 5;
        }

        return ans;
    }
}