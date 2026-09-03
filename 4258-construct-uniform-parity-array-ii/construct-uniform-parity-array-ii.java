class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = nums1[0];

        boolean allSame = true;
        int parity = nums1[0] % 2;

        for (int i = 1; i < nums1.length; i++) {
            min = Math.min(min, nums1[i]);

            if (nums1[i] % 2 != parity) {
                allSame = false;
            }
        }

        // Already all odd or all even
        if (allSame) {
            return true;
        }

        // If minimum is odd, make all elements odd
        return min % 2 == 1;
    }
}