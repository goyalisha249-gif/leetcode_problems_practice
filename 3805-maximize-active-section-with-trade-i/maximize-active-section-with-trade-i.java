import java.util.*;

class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        String t = "1" + s + "1";
        int n = t.length();
        
        // Build run-length blocks: (char, length)
        List<Character> blockChar = new ArrayList<>();
        List<Integer> blockLen = new ArrayList<>();
        
        int i = 0;
        while (i < n) {
            int j = i;
            while (j < n && t.charAt(j) == t.charAt(i)) {
                j++;
            }
            blockChar.add(t.charAt(i));
            blockLen.add(j - i);
            i = j;
        }
        
        // Count original 1's in s
        int totalOnes = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') totalOnes++;
        }
        
        // Find best interior 1-block: max(Z_before + Z_after)
        int maxGain = 0;
        int numBlocks = blockChar.size();
        for (int k = 1; k < numBlocks - 1; k++) { // skip first & last (boundary) blocks
            if (blockChar.get(k) == '1') {
                int gain = blockLen.get(k - 1) + blockLen.get(k + 1);
                maxGain = Math.max(maxGain, gain);
            }
        }
        
        return totalOnes + maxGain;
    }
}