class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int[] ans = {-1, -1};

        ListNode prev = head;
        ListNode curr = head.next;

        int position = 1;

        int first = -1;
        int prevCritical = -1;

        int minDistance = Integer.MAX_VALUE;
        int maxDistance = 0;

        while (curr.next != null) {

            ListNode next = curr.next;

            // Check if current node is a critical point
            boolean isMax = curr.val > prev.val && curr.val > next.val;
            boolean isMin = curr.val < prev.val && curr.val < next.val;

            if (isMax || isMin) {

                // First critical point
                if (first == -1) {
                    first = position;
                } 
                else {
                    // Distance from previous critical point
                    minDistance = Math.min(
                        minDistance,
                        position - prevCritical
                    );

                    // Distance from first critical point
                    maxDistance = position - first;
                }

                prevCritical = position;
            }

            prev = curr;
            curr = next;
            position++;
        }

        // Fewer than 2 critical points
        if (minDistance == Integer.MAX_VALUE) {
            return new int[]{-1, -1};
        }

        return new int[]{minDistance, maxDistance};
    }
}