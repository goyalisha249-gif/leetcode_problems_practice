
class Solution {
    public int minMoves(int[] nums) {

        int min = nums[0];

        // Find minimum element
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
            }
        }

        int moves = 0;

        // Calculate total moves
        for (int i = 0; i < nums.length; i++) {
            moves += nums[i] - min;
        }

        return moves;
    }
}
