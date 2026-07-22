import java.util.*;

class Solution {
    static class SparseTable {
        int[][] st;

        SparseTable(int[] arr) {
            int n = arr.length;

            if (n == 0) {
                st = new int[1][0];
                return;
            }

            int levels = 1;
            while ((1 << levels) <= n) {
                levels++;
            }

            st = new int[levels][n];
            System.arraycopy(arr, 0, st[0], 0, n);

            for (int level = 1; level < levels; level++) {
                int length = 1 << level;
                int half = length >> 1;

                for (int i = 0; i + length <= n; i++) {
                    st[level][i] = Math.max(
                        st[level - 1][i],
                        st[level - 1][i + half]
                    );
                }
            }
        }

        int query(int left, int right) {
            int length = right - left + 1;
            int level = 31 - Integer.numberOfLeadingZeros(length);

            return Math.max(
                st[level][left],
                st[level][right - (1 << level) + 1]
            );
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(
            String s, int[][] queries) {

        int n = s.length();
        int totalOnes = 0;

        // zeroGroup[i] = index of the latest zero-group at or before i.
        int[] zeroGroup = new int[n];
        int[] start = new int[n];
        int[] length = new int[n];

        int groups = 0;

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if (ch == '1') {
                totalOnes++;
            } else {
                if (i == 0 || s.charAt(i - 1) == '1') {
                    start[groups] = i;
                    length[groups] = 0;
                    groups++;
                }

                length[groups - 1]++;
            }

            zeroGroup[i] = groups - 1;
        }

        // mergeValue[i] = length of zero-group i + zero-group i+1
        int[] mergeValue = new int[Math.max(0, groups - 1)];

        for (int i = 0; i + 1 < groups; i++) {
            mergeValue[i] = length[i] + length[i + 1];
        }

        SparseTable sparseTable = new SparseTable(mergeValue);
        List<Integer> answer = new ArrayList<>(queries.length);

        for (int[] query : queries) {
            int l = query[0];
            int r = query[1];

            int bestGain = 0;

            // Remaining part of the zero-block containing l.
            int leftPart = -1;
            if (zeroGroup[l] != -1) {
                int group = zeroGroup[l];
                leftPart = length[group] - (l - start[group]);
            }

            // Prefix part of the zero-block containing r.
            int rightPart = -1;
            if (zeroGroup[r] != -1) {
                int group = zeroGroup[r];
                rightPart = r - start[group] + 1;
            }

            int firstFullGroup = zeroGroup[l] + 1;
            int lastFullGroup =
                (s.charAt(r) == '1') ? zeroGroup[r] : zeroGroup[r] - 1;

            // Two completely contained adjacent zero-groups.
            int pairLeft = firstFullGroup;
            int pairRight = lastFullGroup - 1;

            if (pairLeft <= pairRight) {
                bestGain = Math.max(
                    bestGain,
                    sparseTable.query(pairLeft, pairRight)
                );
            }

            // The query starts inside a zero-group.
            if (s.charAt(l) == '0' && firstFullGroup <= lastFullGroup) {
                bestGain = Math.max(
                    bestGain,
                    leftPart + length[firstFullGroup]
                );
            }

            // The query ends inside a zero-group.
            if (s.charAt(r) == '0'
                    && zeroGroup[l] < zeroGroup[r] - 1) {
                bestGain = Math.max(
                    bestGain,
                    length[zeroGroup[r] - 1] + rightPart
                );
            }

            // Query contains exactly two partial zero-groups: 0...0 1...1 0...0
            if (s.charAt(l) == '0'
                    && s.charAt(r) == '0'
                    && zeroGroup[l] + 1 == zeroGroup[r]) {
                bestGain = Math.max(bestGain, leftPart + rightPart);
            }

            answer.add(totalOnes + bestGain);
        }

        return answer;
    }
}