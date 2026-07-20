import java.util.*;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>(); // value -> index
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            if (seen.containsKey(complement)) {
                return new int[] { seen.get(complement), i };
            }
            
            seen.put(nums[i], i);
        }
        
        // Problem guarantees a solution exists, so this line is unreachable
        throw new IllegalArgumentException("No two sum solution");
    }
}