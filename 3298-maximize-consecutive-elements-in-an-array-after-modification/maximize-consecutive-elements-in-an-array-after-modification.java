import java.util.Arrays;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        int maxVal = nums[nums.length - 1];
        int[] dp = new int[maxVal + 3];
        int ans = 1;

        for (int x : nums) {
            dp[x + 1] = dp[x] + 1;   // use x as x+1 (do this first)
            dp[x] = dp[x - 1] + 1;   // keep x as x
            ans = Math.max(ans, Math.max(dp[x], dp[x + 1]));
        }
        return ans;
    }
}