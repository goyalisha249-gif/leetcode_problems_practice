import java.util.*;

class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;
        
        // Flatten
        int[] flat = new int[total];
        int idx = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                flat[idx++] = grid[i][j];
            }
        }
        
        // Reduce k
        k %= total;
        
        // Rotate right by k
        int[] rotated = new int[total];
        for (int i = 0; i < total; i++) {
            rotated[(i + k) % total] = flat[i];
        }
        
        // Reshape into m x n
        List<List<Integer>> result = new ArrayList<>();
        idx = 0;
        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(rotated[idx++]);
            }
            result.add(row);
        }
        
        return result;
    }
}