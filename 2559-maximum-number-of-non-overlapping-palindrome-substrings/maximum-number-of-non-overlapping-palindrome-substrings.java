class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        // palindrome[i][j] = true if s[i...j] is a palindrome
        boolean[][] palindrome = new boolean[n][n];

        // Build palindrome table
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;

                if (len == 1) {
                    palindrome[i][j] = true;
                } 
                else if (len == 2) {
                    palindrome[i][j] = (s.charAt(i) == s.charAt(j));
                } 
                else {
                    palindrome[i][j] =
                        (s.charAt(i) == s.charAt(j)) &&
                        palindrome[i + 1][j - 1];
                }
            }
        }

        // dp[i] = maximum palindromes using first i characters
        int[] dp = new int[n + 1];

        for (int r = 1; r <= n; r++) {

            // Don't select a palindrome ending at r-1
            dp[r] = dp[r - 1];

            // Try every starting position
            for (int l = 0; l < r; l++) {

                int len = r - l;

                if (len >= k && palindrome[l][r - 1]) {
                    dp[r] = Math.max(dp[r], dp[l] + 1);
                }
            }
        }

        return dp[n];
    }
}