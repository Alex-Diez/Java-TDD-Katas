package kata.java;

import java.util.stream.Stream;

public class BTreeSet {
    private final int pageLength;
    private Page root;
    private int height;

    public BTreeSet(int pageLength) {
        this.pageLength = pageLength;
        this.root = new Page(0);
        this.height = 0;
    }

    public boolean contains(int key) {
        Page page = root;
        int index = page.indexOf(key);
        while (index > -1 && page.entries[index].next != null) {
            page = page.entries[index].next;
            index = page.indexOf(key);
        }
        return page.contains(key);
    }

    public void add(int key) {
//        Page page = root;
//        int index = page.indexOf(key);
//        if (index == pageLength) {
//            int aHalf = pageLength / 2;
//            Page aLeftPage = new Page(aHalf);
//            for (int i = 0; i < aHalf; i++) {
//                aLeftPage.entries[i] = page.entries[i];
//            }
//            Page aRightPage = new Page(aHalf);
//            for (int i = aHalf; i < pageLength; i++) {
//                aRightPage.entries[i - aHalf] = page.entries[i];
//            }
//            page.size = 2;
//            page.entries[0] = new Entry(aLeftPage.entries[0].key, aLeftPage);
//            page.entries[1] = new Entry(aRightPage.entries[0].key, aRightPage);
//        }
//        while (page.entries[index].next != null) {
//            Page parent = page;
//            page = parent.entries[index].next;
//            int indexPage = page.indexOf(key);
//            if (indexPage == pageLength) {
//                Page aRight = page.split();
//                int indexParent = parent.indexOf(key);
//
//            }
//        }

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
        Entry entry = new Entry(key, null);
        int index = page.indexOf(key);
        if (height != 0) {
            Page local = add(page.entries[index].next, key, height - 1);
            if (local != null) {
                entry = new Entry(local.entries[0].key, local);
            }
            else {
                return null;
            }
        }
        index += 1;

        page.insert(index, entry);
        if (page.size < pageLength) return null;
        else return page.split();
    }

    private class Page {
        private final Entry[] entries = new Entry[pageLength];
        private int size;

        Page(int size) {
            this.size = size;
        }

        int indexOf(int key) {
            return (int) entries().filter(e -> e.key <= key).count() - 1;
        }

        private Stream<Entry> entries() {
            return Stream.of(entries).limit(size);
        }

        boolean contains(int key) {
            return entries().anyMatch(e -> e.key == key);
        }

        Page split() {
            int aHalf = pageLength / 2;
            Page aNewPage = new Page(aHalf);
            size = aHalf;
            System.arraycopy(entries, aHalf, aNewPage.entries, 0, pageLength - aHalf);
            return aNewPage;
        }

        void insert(int index, Entry entry) {
            shift(index);
            entries[index] = entry;
            size += 1;
        }

        private void shift(int index) {
            System.arraycopy(entries, index, entries, index + 1, size - index);
        }

        boolean isFull() {
            return size == pageLength;
        }
    }

    private class Entry {
        private final int key;
        private final Page next;

        Entry(int key, Page next) {
            this.key = key;
            this.next = next;
        }
    }
}
