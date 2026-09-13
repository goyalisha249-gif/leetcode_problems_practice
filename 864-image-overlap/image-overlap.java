class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        int maxOverlap = 0;

        // Try every possible shift
        for (int rowShift = -n + 1; rowShift < n; rowShift++) {
            for (int colShift = -n + 1; colShift < n; colShift++) {

                int overlap = 0;

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {

                        int newRow = i + rowShift;
                        int newCol = j + colShift;

                        // Check if translated position is inside img2
                        if (newRow >= 0 && newRow < n &&
                            newCol >= 0 && newCol < n) {

                            if (img1[i][j] == 1 && img2[newRow][newCol] == 1) {
                                overlap++;
                            }
                        }
                    }
                }

                maxOverlap = Math.max(maxOverlap, overlap);
            }
        }

        return maxOverlap;
    }
}