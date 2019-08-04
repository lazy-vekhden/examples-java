package com.labean.sqrttree.leetcode;

import com.labean.sqrttree.LongSqrtTree;

public class ReversePairs {
    //  https://leetcode.com/problems/reverse-pairs/

    public int reversePairs(int[] nums) {
        LongSqrtTree tree = new LongSqrtTree();
        int ans = 0;
        for(int i : nums) {
            ans+=tree.moreOrEquals(2L*i+1);
            tree.add(i);
        }
        return ans;
    }

}
