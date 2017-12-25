package kata.java;

public class BTreeSet {
    private final int pageLength;
    private Page root;
    private int height;

    public BTreeSet(int pageLength) {
        this.pageLength = pageLength;
        this.root = new Page(this.pageLength);
        this.height = 1;
    }

    public boolean contains(int key) {
        return contains(root, key, height);
    }

    private boolean contains(Page page, int key, int height) {
        if (height == 1) {
            for (Entry e : page.keys) {
                if (e != null && e.key == key) return true;
            }
            return false;
        }
        else {
            for (int i = 1; i < pageLength; i++) {
                if (page.keys[i-1] != null && (page.keys[i] == null || key < page.keys[i].key))
                    return contains(page.keys[i-1].next, key, height - 1);
            }
            return false;
        }
    }

    public void add(int key) {
        if (root.add(key, height)) {
            height += 1;
        }
    }

    public int height() {
        return height;
    }

    private class Page {
        private Entry[] keys;
        private int size;

        private Page(int pageLength) {
            keys = new Entry[pageLength];
            size = 0;
        }

        private boolean add(int key, int height) {
            if (height == 1) {
                if (size < pageLength) {
                    keys[size] = new Entry(key);
                    size += 1;
                    return false;
                }
                else {
                    split(key);
                    return true;
                }
            }
            else {
                for (int i = 1; i < pageLength; i++) {
                    if (keys[i-1] != null && (keys[i] == null || key < keys[i].key)) {
                        return keys[i - 1].next.add(key, height - 1);
                    }
                }
                return false;
            }
        }

        private void split(int key) {
            Page left = new Page(pageLength);
            Page right = new Page(pageLength);
            int leftLength = pageLength / 2 + 1;
            fillPage(left, 0, leftLength);
            fillPage(right, leftLength, pageLength);
            right.add(key, 1);
            balance(left, right);
        }

        private void balance(BTreeSet.Page left, BTreeSet.Page right) {
            keys[0].key = left.keys[0].key;
            keys[0].next = left;
            keys[1].key = right.keys[0].key;
            keys[1].next = right;
            cleanUp();
        }

        private void cleanUp() {
            size = 2;
            for (int i = 2; i < pageLength; i++) {
                keys[i] = null;
            }
        }

        private void fillPage(Page page, int from, int length) {
            for (int i = from; i < length; i++) {
                page.add(keys[i].key, 1);
            }
        }

    }

    private class Entry {
        private int key;
        private Page next;

        public Entry(int key) {
            this.key = key;
        }
    }
}
