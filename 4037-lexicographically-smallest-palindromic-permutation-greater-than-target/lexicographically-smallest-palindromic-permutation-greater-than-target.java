class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // Count characters
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Check if palindrome is possible
        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                middle = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        // Frequency for first half
        int halfLen = n / 2;
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        /*
         * STEP 1:
         * Try to make first half exactly equal to
         * target's first half.
         */
        int[] rem = halfFreq.clone();
        char[] half = new char[halfLen];

        boolean possible = true;

        for (int i = 0; i < halfLen; i++) {

            int c = target.charAt(i) - 'a';

            if (rem[c] == 0) {
                possible = false;
                break;
            }

            half[i] = (char) ('a' + c);
            rem[c]--;
        }

        /*
         * If the first half can equal target's first half,
         * construct the palindrome and check it.
         */
        if (possible) {

            String candidate = makePalindrome(half, middle, n);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * STEP 2:
         * First half equal to target did not work.
         *
         * Find the smallest first half that is
         * lexicographically greater than target's first half.
         */
        for (int pos = halfLen - 1; pos >= 0; pos--) {

            rem = halfFreq.clone();

            // Keep everything before pos equal to target
            boolean prefixPossible = true;

            for (int i = 0; i < pos; i++) {

                int c = target.charAt(i) - 'a';

                if (rem[c] == 0) {
                    prefixPossible = false;
                    break;
                }

                rem[c]--;
            }

            if (!prefixPossible) {
                continue;
            }

            int targetChar = target.charAt(pos) - 'a';

            // Choose smallest character greater than target[pos]
            for (int c = targetChar + 1; c < 26; c++) {

                if (rem[c] == 0) {
                    continue;
                }

                char[] newHalf = new char[halfLen];

                // Copy prefix
                for (int i = 0; i < pos; i++) {
                    newHalf[i] = target.charAt(i);
                }

                // Make this position larger
                newHalf[pos] = (char) ('a' + c);

                rem[c]--;

                // Fill remaining positions with smallest characters
                int index = pos + 1;

                for (int x = 0; x < 26; x++) {

                    while (rem[x] > 0) {
                        newHalf[index++] = (char) ('a' + x);
                        rem[x]--;
                    }
                }

                return makePalindrome(newHalf, middle, n);
            }
        }

        return "";
    }

    private String makePalindrome(char[] half, int middle, int n) {

        StringBuilder ans = new StringBuilder();

        // First half
        for (char c : half) {
            ans.append(c);
        }

        // Middle character
        if (n % 2 == 1) {
            ans.append((char) ('a' + middle));
        }

        // Reverse first half
        for (int i = half.length - 1; i >= 0; i--) {
            ans.append(half[i]);
        }

        return ans.toString();
    }
}