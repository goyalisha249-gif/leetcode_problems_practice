import java.util.Arrays;

class Solution {
    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        
        // Option 1: three largest numbers
        int candidate1 = nums[n - 1] * nums[n - 2] * nums[n - 3];
        
        // Option 2: two smallest (possibly large negatives) * largest
        int candidate2 = nums[0] * nums[1] * nums[n - 1];
        
        return Math.max(candidate1, candidate2);
    }
}