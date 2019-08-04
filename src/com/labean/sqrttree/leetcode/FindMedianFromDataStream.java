package com.labean.sqrttree.leetcode;

import com.labean.sqrttree.LongSqrtTree;

public class FindMedianFromDataStream {
    // https://leetcode.com/problems/find-median-from-data-stream/

    LongSqrtTree tree;

    /**
     * initialize your data structure here.
     */
    public FindMedianFromDataStream() {
        tree = new LongSqrtTree();
    }

    public void addNum(int num) {
        tree.add(num);
    }

    public double findMedian() {
        int k = tree.size();
        if (k % 2 == 1) return tree.kth(k / 2);
        else return 0.5 * (tree.kth(k / 2) + tree.kth(k / 2 - 1));
    }

}
