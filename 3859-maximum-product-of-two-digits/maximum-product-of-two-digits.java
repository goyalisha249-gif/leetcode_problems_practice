class Solution {
    public int maxProduct(int n) {
        String s = Integer.toString(n);
        int[] digits = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            digits[i] = s.charAt(i) - '0';
        }
        Arrays.sort(digits);
        int len = digits.length;
        return digits[len - 1] * digits[len - 2];
    }
}