class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder ans = new StringBuilder(target);

        // Try making the first difference as far right as possible
        for (int i = n - 1; i >= 0; i--) {

            // Characters used by target[0 ... i-1]
            int[] remaining = freq.clone();

            boolean possible = true;

            for (int j = 0; j < i; j++) {
                int idx = target.charAt(j) - 'a';

                if (remaining[idx] == 0) {
                    possible = false;
                    break;
                }

                remaining[idx]--;
            }

            if (!possible) {
                continue;
            }

            int cur = target.charAt(i) - 'a';

            // Find smallest available character > target[i]
            int bigger = -1;

            for (int c = cur + 1; c < 26; c++) {
                if (remaining[c] > 0) {
                    bigger = c;
                    break;
                }
            }

            if (bigger != -1) {

                StringBuilder result = new StringBuilder();

                // Same prefix as target
                for (int j = 0; j < i; j++) {
                    result.append(target.charAt(j));
                }

                // First character that is greater
                result.append((char) ('a' + bigger));
                remaining[bigger]--;

                // Fill the rest with smallest possible characters
                for (int c = 0; c < 26; c++) {
                    while (remaining[c] > 0) {
                        result.append((char) ('a' + c));
                        remaining[c]--;
                    }
                }

                return result.toString();
            }
        }

        return "";
    }
}