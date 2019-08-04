package com.labean.sqrttree.leetcode;

import com.labean.sqrttree.LongSqrtTree;

import java.util.ArrayList;
import java.util.List;

public class CountOfSmallerNumbersAfterSelf {
    // https://leetcode.com/problems/count-of-smaller-numbers-after-self/

    public List<Integer> countSmaller(int[] nums) {
        LongSqrtTree tree = new LongSqrtTree();
        for (int i : nums) tree.add(i);
        List<Integer> ans = new ArrayList<>();
        for (int i : nums) {
            tree.remove(i);
            ans.add(tree.lessOrEquals(i - 1L));
        }
        return ans;
    }
}
