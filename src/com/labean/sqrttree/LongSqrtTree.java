package com.labean.sqrttree;

public class LongSqrtTree {
    public static final int MIN_MAX_SIZE = 1024;

    private int maxSize;
    private int maxHeight;
    private int count;

    private class TreeElem {
        int subTreeSize = 1;
        long key;

        TreeElem parent;
        TreeElem less;
        TreeElem more;

    }

    private TreeElem root;

    public LongSqrtTree() {
        maxSize = MIN_MAX_SIZE;
        maxHeight = (int) Math.sqrt(maxSize);
        count = 0;
    }


    public long kth(int k) {
        if (k >= count || k < 0) throw new IndexOutOfBoundsException("count: " + count + ", k: " + k);
        return kth(k, root);

    }

    private long kth(int k, TreeElem elem) {
        int prev = (elem.less == null) ? 0 : elem.less.subTreeSize;
        if (prev == k) return elem.key;
        else if (prev > k) return kth(k, elem.less);
        else return kth(k - prev - 1, elem.more);
    }

    public void add(long key) {
        if (root == null) {
            root = new TreeElem();
            root.subTreeSize = 1;
            root.key = key;
            count = 1;
            return;
        }

        TreeElem elem = root;
        int deep = 0;

        while (true) {
            ++elem.subTreeSize;
            ++deep;
            if (elem.key < key) {
                if (elem.more == null) {
                    elem.more = new TreeElem();
                    elem.more.key = key;
                    elem.more.parent = elem;
                    break;
                } else elem = elem.more;

            } else {
                if (elem.less == null) {
                    elem.less = new TreeElem();
                    elem.less.key = key;
                    elem.less.parent = elem;
                    break;
                } else elem = elem.less;
            }
        }

        ++count;
        if (count == maxSize) {
            maxSize *= 2;
            maxHeight = (int) Math.sqrt(maxSize);
            return;
        }

        if (deep > maxHeight) rebalance();

    }

    public void remove(long key) {
        TreeElem elem = root;
        while (elem != null) {
            if (elem.key == key) {
                remove(elem);
                return;
            }
            if (elem.key < key) elem = elem.more;
            else elem = elem.less;
        }
    }


    public int size() {
        return count;
    }


    public int inDiap(long fromInclusive, long toInclusive) {
        TreeElem elem = root;
        while (elem != null) {
            if (elem.key > toInclusive) elem = elem.less;
            else if (elem.key < fromInclusive) elem = elem.more;
            else return 1 + lessOrEquals(toInclusive, elem.more) + moreOrEquals(fromInclusive, elem.less);

        }
        return 0;
    }


    public int lessOrEquals(long n) {
        return lessOrEquals(n, root);
    }

    public int moreOrEquals(long n) {
        return moreOrEquals(n, root);
    }

    private int lessOrEquals(long n, TreeElem elem) {
        if (elem == null) return 0;
        if (elem.key > n) return lessOrEquals(n, elem.less);
        return 1 +
                (elem.less != null ? elem.less.subTreeSize : 0)
                + lessOrEquals(n, elem.more);
    }

    private int moreOrEquals(long n, TreeElem elem) {
        if (elem == null) return 0;
        if (elem.key < n) return moreOrEquals(n, elem.more);
        return 1 +
                (elem.more != null ? elem.more.subTreeSize : 0)
                + moreOrEquals(n, elem.less);
    }

    private void remove(TreeElem elem) {
        TreeElem nextToRem = neibourElem(elem);
        if (nextToRem == null) {
            removeLeaf(elem);
        } else {
            elem.key = nextToRem.key;
            remove(nextToRem);
        }
    }

    private void removeLeaf(TreeElem elem) {
        if (elem == root) {
            root = null;
            count = 0;
            return;
        }

        TreeElem p = elem.parent;
        if (p.more == elem) p.more = null;
        else p.less = null;

        while (p != null) {
            --p.subTreeSize;
            p = p.parent;
        }

        --count;
        if (maxSize != MIN_MAX_SIZE) {
            if (count * 4 == maxSize) {
                rebalance();
                maxSize /= 2;
                maxHeight = (int) Math.sqrt(maxSize);
            }
        }
    }

    private TreeElem neibourElem(TreeElem elem) {
        if (elem.less != null) {
            TreeElem e = elem.less;
            while (e.more != null) e = e.more;
            return e;
        }

        if (elem.more != null) {
            TreeElem e = elem.more;
            while (e.less != null) e = e.less;
            return e;
        }

        return null;
    }

    private void rebalance() {
        long[] values = new long[count];
        int[] pos = new int[1];

        dumpToArray(root, values, pos);
        root = fromArray(values, 0, count);
    }

    private TreeElem fromArray(long[] values, int from, int to) {
        if (from >= to) return null;
        int mid = (from + to) / 2;
        TreeElem ret = new TreeElem();
        ret.key = values[mid];
        ret.more = fromArray(values, mid + 1, to);
        ret.less = fromArray(values, from, mid);

        if (ret.less != null) {
            ret.subTreeSize += ret.less.subTreeSize;
            ret.less.parent = ret;
        }

        if (ret.more != null) {
            ret.subTreeSize += ret.more.subTreeSize;
            ret.more.parent = ret;
        }
        return ret;
    }

    private void dumpToArray(TreeElem elem, long[] values, int[] pos) {
        if (elem == null) return;
        dumpToArray(elem.less, values, pos);
        values[pos[0]++] = elem.key;
        dumpToArray(elem.more, values, pos);
    }

}
