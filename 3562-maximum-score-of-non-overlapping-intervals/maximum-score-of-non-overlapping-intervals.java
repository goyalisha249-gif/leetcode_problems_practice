import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class State {
        long score;
        ArrayList<Integer> indices;

        State(long score, ArrayList<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        Interval[] a = new Interval[n];

        for (int i = 0; i < n; i++) {
            a[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        // Sort by ending point
        Arrays.sort(a, (x, y) -> {
            if (x.r != y.r)
                return Integer.compare(x.r, y.r);
            return Integer.compare(x.idx, y.idx);
        });

        // Find previous non-overlapping interval
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {

            int low = 0;
            int high = i - 1;
            int ans = -1;

            while (low <= high) {

                int mid = low + (high - low) / 2;

                // Strictly less because touching boundaries overlap
                if (a[mid].r < a[i].l) {
                    ans = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            prev[i] = ans;
        }

        // dp[i][k] = best answer using first i intervals
        // and choosing at most k intervals
        State[][] dp = new State[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] =
                    new State(0, new ArrayList<>());
            }
        }

        for (int i = 1; i <= n; i++) {

            // Don't choose interval i-1
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = dp[i - 1][k];
            }

            // Choose interval i-1
            for (int k = 1; k <= 4; k++) {

                int p = prev[i - 1] + 1;

                State old = dp[p][k - 1];

                long newScore = old.score + a[i - 1].w;

                ArrayList<Integer> newList =
                    new ArrayList<>(old.indices);

                newList.add(a[i - 1].idx);

                Collections.sort(newList);

                State candidate =
                    new State(newScore, newList);

                if (better(candidate, dp[i][k])) {
                    dp[i][k] = candidate;
                }
            }
        }

        ArrayList<Integer> answer = dp[n][4].indices;

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }

    static boolean better(State a, State b) {

        // Higher score is better
        if (a.score != b.score) {
            return a.score > b.score;
        }

        // Same score -> lexicographically smaller
        return lexicographicallySmaller(a.indices, b.indices);
    }

    static boolean lexicographicallySmaller(
        ArrayList<Integer> a,
        ArrayList<Integer> b
    ) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }

        return a.size() < b.size();
    }
}