
import java.util.*;

class Solution {

    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();

        // Queue for BFS
        Queue<String> queue = new LinkedList<>();
        queue.add(s);

        // To avoid duplicate strings
        Set<String> visited = new HashSet<>();
        visited.add(s);

        // Once we find valid strings, don't generate next level
        boolean found = false;

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Process one BFS level
            for (int i = 0; i < size; i++) {

                String current = queue.poll();

                // Check whether current string is valid
                if (isValid(current)) {
                    result.add(current);
                    found = true;
                }

                // If valid strings are already found,
                // don't remove more characters
                if (found) {
                    continue;
                }

                // Remove one character at every position
                for (int j = 0; j < current.length(); j++) {

                    // Removing a letter is unnecessary
                    if (current.charAt(j) != '(' &&
                        current.charAt(j) != ')') {
                        continue;
                    }

                    String next = current.substring(0, j)
                                 + current.substring(j + 1);

                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.add(next);
                    }
                }
            }

            // We found valid strings at this level,
            // so minimum removals have been made.
            if (found) {
                break;
            }
        }

        return result;
    }

    // Checks whether parentheses are valid
    private boolean isValid(String s) {

        int balance = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (ch == '(') {
                balance++;
            }
            else if (ch == ')') {
                balance--;

                // More closing brackets than opening brackets
                if (balance < 0) {
                    return false;
                }
            }
        }

        // All opening brackets must also be closed
        return balance == 0;
    }
}