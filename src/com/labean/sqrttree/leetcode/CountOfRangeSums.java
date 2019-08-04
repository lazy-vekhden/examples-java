package com.labean.sqrttree.leetcode;

import com.labean.sqrttree.LongSqrtTree;

public class CountOfRangeSums {
    // https://leetcode.com/problems/count-of-range-sum/

    public int countRangeSum(int[] nums, int lower, int upper) {
        LongSqrtTree tree = new LongSqrtTree();
        tree.add(0);
        long sum = 0;
        int ans = 0;
        for (int i : nums) {
            sum += i;
            ans += tree.inDiap(sum - upper, sum - lower);
            tree.add(sum);
        }
        return ans;
    }
}

