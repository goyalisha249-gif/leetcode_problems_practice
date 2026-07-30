class Solution {
    public int minimumPushes(String word) {
        int n = word.length();
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += 1 + i / 8;
        }
        return total;
    }
}