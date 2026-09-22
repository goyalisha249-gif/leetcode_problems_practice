
class Solution {

    int n, k;
    Node[] tree;

    class Node {
        int prod;
        int[] cnt;

        Node() {
            cnt = new int[k];
        }
    }

    Node makeNode(int value) {
        Node node = new Node();

        int rem = value % k;

        node.prod = rem;
        node.cnt[rem] = 1;

        return node;
    }

    Node merge(Node left, Node right) {

        Node result = new Node();

        // Product of the complete segment
        result.prod = (left.prod * right.prod) % k;

        // Prefixes completely inside left
        for (int r = 0; r < k; r++) {
            result.cnt[r] += left.cnt[r];
        }

        // Prefixes that cross from left into right
        for (int r = 0; r < k; r++) {

            if (right.cnt[r] == 0)
                continue;

            int newRem = (left.prod * r) % k;

            result.cnt[newRem] += right.cnt[r];
        }

        return result;
    }

    void build(int node, int start, int end, int[] nums) {

        if (start == end) {
            tree[node] = makeNode(nums[start]);
            return;
        }

        int mid = (start + end) / 2;

        build(node * 2, start, mid, nums);
        build(node * 2 + 1, mid + 1, end, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int start, int end,
                int index, int value) {

        if (start == end) {
            tree[node] = makeNode(value);
            return;
        }

        int mid = (start + end) / 2;

        if (index <= mid) {
            update(node * 2, start, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, end, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    Node query(int node, int start, int end,
               int left, int right) {

        if (left <= start && end <= right) {
            return tree[node];
        }

        int mid = (start + end) / 2;

        if (right <= mid) {
            return query(node * 2, start, mid, left, right);
        }

        if (left > mid) {
            return query(node * 2 + 1, mid + 1, end, left, right);
        }

        Node leftNode = query(
            node * 2,
            start,
            mid,
            left,
            right
        );

        Node rightNode = query(
            node * 2 + 1,
            mid + 1,
            end,
            left,
            right
        );

        return merge(leftNode, rightNode);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.n = nums.length;
        this.k = k;

        tree = new Node[4 * n];

        // Build segment tree
        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Update nums[index]
            nums[index] = value;

            // Update segment tree
            update(1, 0, n - 1, index, value);

            // Query range [start, n-1]
            Node ans = query(
                1,
                0,
                n - 1,
                start,
                n - 1
            );

            // Number of prefix products having remainder x
            result[i] = ans.cnt[x];
        }

        return result;
    }
}
