import java.util.TreeMap;

class Solution {
    public String smallestPalindrome(String s) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }
        
        StringBuilder half = new StringBuilder();
        char mid = '\0';
        boolean hasMid = false;
        
        for (int i = 0; i < 26; i++) {
            char c = (char) ('a' + i);
            int half_count = cnt[i] / 2;
            for (int j = 0; j < half_count; j++) {
                half.append(c);
            }
            if (cnt[i] % 2 == 1) {
                mid = c;
                hasMid = true;
            }
        }
        
        StringBuilder result = new StringBuilder();
        result.append(half);
        if (hasMid) {
            result.append(mid);
        }
        result.append(half.reverse());
        
        return result.toString();
    }
}