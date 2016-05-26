package kata.java;

import java.util.stream.Stream;

public class BTreeSet {
    private final int pageLength;
    private Page root;
    private int height;

    public BTreeSet(int pageLength) {
        this.pageLength = pageLength;
        root = new Page(0);
    }

    public boolean contains(int key) {
        return contains(root, key, height);
    }

    private boolean contains(Page page, int key, int height) {
        if (height == 0) {
            return page.entries().anyMatch(e -> e.key == key);
        }
        else {
            int i = (int) page.entries().filter(e -> key >= e.key).count() - 1;
            return contains(page.entries[i].next, key, height - 1);
        }
    }

    public void add(int key) {
        Page page = add(root, key, height);
        if (page != null) {
            Page local = new Page(2);
            local.entries[0] = new Entry(root.entries[0].key, root);
            local.entries[1] = new Entry(page.entries[0].key, page);
            root = local;
            height += 1;
        }
    }

    private Page add(Page page, int key, int height) {
        int index;
        Entry entry = new Entry(key, null);
        if (height == 0) {
            index = (int) page.entries().filter(e -> key > e.key).count();
        }
        else {
            index = (int) page.entries().filter(e -> key > e.key).count() - 1;
            Page local = add(page.entries[index++].next, key, height - 1);
            if (local != null) {
                entry.key = local.entries[0].key;
                entry.next = local;
            }
            else return null;
        }
        page.insert(index, entry);
        if (page.size < pageLength) return null;
        else return page.split();
    }

    private class Page {
        private Entry[] entries = new Entry[pageLength];
        private int size;

        private Page(int size) {
            this.size = size;
        }

        private void insert(int index, Entry entry) {
            shiftRight(index);
            entries[index] = entry;
            size += 1;
        }

        private void shiftRight(int from) {
            System.arraycopy(entries, from, entries, from + 1, size - from);
        }

        private Stream<Entry> entries() {
            return Stream.of(entries).limit(size);
        }

        private Page split() {
            int aHalf = pageLength / 2;
            Page newPage = new Page(aHalf);
            size = aHalf;
            System.arraycopy(entries, aHalf, newPage.entries, 0, pageLength - aHalf);
            return newPage;
        }
    }

    private class Entry {
        private int key;
        private Page next;

        private Entry(int key, Page next) {
            this.key = key;
            this.next = next;
        }
    }
}
