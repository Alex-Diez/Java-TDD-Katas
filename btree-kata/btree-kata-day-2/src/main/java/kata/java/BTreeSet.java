package kata.java;

import java.util.Arrays;

public class BTreeSet {
    private final int pageSize;
    private Page root;
    private int height;

    public BTreeSet(int pageSize) {
        this.pageSize = pageSize;
        this.root = new Page();
        this.height = 1;
    }

    public boolean contain(int key) {
        return contain(root, key, height);
    }

    private boolean contain(Page page, int key, int height) {
        if (height == 1) {
            for (Entity e : page.keys) {
                if (e == null) {
                    return false;
                }
                if (e.key == key) {
                    return true;
                }
            }
            return false;
        }
        else {
            for (int i = 1; i < pageSize; i++) {
                if (page.keys[i - 1] == null) {
                    return false;
                }
                if (page.keys[i] == null || key < page.keys[i].key) {
                    return contain(page.keys[i - 1].next, key, height - 1);
                }
            }
            return false;
        }
    }

    public void add(int key) {
        add(root, key, height);
    }

    private void add(Page page, int key, int height) {
        int i = 0;
        if (height == 1) {
            while (i < pageSize && page.keys[i] != null && page.keys[i].key < key) {
                i++;
            }
        }
        else {
            while (i < pageSize) {
                if (page.keys[i - 1] == null) {
                    return;
                }
                if (page.keys[i] == null || key < page.keys[i].key) {
                    add(page.keys[i - 1].next, key, height - 1);
                }
                i++;
            }
        }
        if (i > pageSize - 1) {
            split(page, key);
            this.height += 1;
        }
        else {
            page.add(key);
        }
    }

    private void split(Page page, int key) {
        Page left = new Page();
        Page right = new Page();
        int leftLength = pageSize / 2 + 1;
        int j = 0;
        while (j < leftLength) {
            left.add(page.keys[j].key);
            j++;
        }
        while (j < pageSize) {
            right.add(page.keys[j].key);
            j++;
        }
        right.add(key);
        page.size = 2;
        page.keys[0].key = left.keys[0].key;
        page.keys[0].next = left;
        page.keys[1].key = right.keys[0].key;
        page.keys[1].next = right;
        for (int i = 2; i < pageSize; i++) {
            page.keys[i] = null;
        }
    }

    public int height() {
        return height;
    }

    private class Page {
        private Entity[] keys;
        private int size;

        private Page() {
            this.keys = new Entity[pageSize];
            this.size = 0;
        }

        @Override
        public String toString() {
            return "{ Page " + Arrays.toString(keys) + " }";
        }

        public void add(int key) {
            keys[size] = new Entity();
            keys[size].key = key;
            size += 1;
        }
    }

    private class Entity {
        private int key;
        private Page next;

        @Override
        public String toString() {
            return "[ Entity key - " + key + " ]";
        }
    }
}
