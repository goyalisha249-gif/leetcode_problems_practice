import java.util.*;

class Solution {
    public int minimumPushes(String word) {

        int[] freq = new int[26];

        // Count frequency
        for (char ch : word.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Store non-zero frequencies
        List<Integer> list = new ArrayList<>();
        for (int f : freq) {
            if (f > 0)
                list.add(f);
        }

        // Sort in descending order
        Collections.sort(list, Collections.reverseOrder());

        int ans = 0;

        for (int i = 0; i < list.size(); i++) {
            int pushes = (i / 8) + 1;
            ans += list.get(i) * pushes;
        }

        return ans;
    }
}