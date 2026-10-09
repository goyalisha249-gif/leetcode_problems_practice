
class Solution {
    public int minInsertions(String s) {
        int open = 0;
        int insertions = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                open++;
            } else {
                // If the next character is also ')',
                // both ')' form one closing pair.
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++;
                } else {
                    // Insert one ')' to complete the pair.
                    insertions++;
                }

                if (open > 0) {
                    open--;
                } else {
                    // No opening '(' available.
                    insertions++;
                }
            }
        }

        // Each unmatched '(' requires two ')'.
        insertions += open * 2;

        return insertions;
    }
}
