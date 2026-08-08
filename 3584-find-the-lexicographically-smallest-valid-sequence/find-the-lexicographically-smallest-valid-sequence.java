class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // dp[i] = number of characters from the end of word2
        // that can be matched using word1[i...n-1]
        int[] dp = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1];

            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                dp[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int i = 0;
        j = 0;

        // First, greedily find the lexicographically smallest
        // indices. We may use one mismatch.
        while (i < n && j < m) {

            // Exact match -> always take it because smaller index
            // gives a lexicographically smaller answer.
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            }

            // Mismatch -> use our one allowed modification,
            // but only if the remaining characters can be matched.
            else if (dp[i + 1] >= m - 1 - j) {
                ans[j] = i;
                j++;
                i++;

                // The one mismatch has been used.
                break;
            }

            i++;
        }

        // We haven't selected all characters.
        if (j < m && i >= n) {
            return new int[0];
        }

        // After using the mismatch (or if no mismatch was needed),
        // match the remaining characters exactly.
        while (j < m && i < n) {
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            }
            i++;
        }

        // Still incomplete => impossible
        if (j < m) {
            return new int[0];
        }

        return ans;
    }
}